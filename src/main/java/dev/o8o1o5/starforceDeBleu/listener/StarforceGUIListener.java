package dev.o8o1o5.starforceDeBleu.listener;

import dev.o8o1o5.starforceDeBleu.StarforceDeBleu;
import dev.o8o1o5.starforceDeBleu.gui.StarforceGUI;
import dev.o8o1o5.starforceDeBleu.manager.StarforceManager;
import dev.o8o1o5.starforceDeBleu.util.StarforceDataUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class StarforceGUIListener implements Listener {
    private final StarforceManager starforceManager;
    private final StarforceDeBleu plugin;

    public StarforceGUIListener(StarforceManager starforceManager, StarforceDeBleu plugin) {
        this.starforceManager = starforceManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // 클릭된 인벤토리가 우리가 만든 StarforceGUI인지 확인 (InventoryHolder를 통해)
        if (!(event.getInventory().getHolder() instanceof StarforceGUI)) {
            return; // 우리가 만든 GUI가 아니면 무시
        }

        Player player = (Player) event.getWhoClicked();
        StarforceGUI gui = (StarforceGUI) event.getInventory().getHolder();

        // 1. GUI 자체의 슬롯 (상단 인벤토리 영역) 클릭 처리
        if (event.getRawSlot() < gui.getInventory().getSize()) {
            event.setCancelled(true); // 기본적으로는 취소

            int clickedSlot = event.getRawSlot();
            ItemStack clickedItem = event.getCurrentItem(); // 클릭된 슬롯에 현재 있는 아이템
            ItemStack cursorItem = event.getCursor();     // 플레이어 마우스 커서에 있는 아이템

            // 1-1. 강화 슬롯 (ENHANCE_SLOT)에 아이템 넣기/빼기 처리
            if (clickedSlot == StarforceGUI.ENHANCE_SLOT) {
                // 커서에 아이템이 있고, 강화 슬롯으로 아이템을 드래그하여 넣을 때
                if (cursorItem != null && !cursorItem.getType().isAir()) {
                    event.setCancelled(false); // 아이템이 슬롯으로 들어오거나 교체되도록 허용
                    // 아이템이 슬롯에 완전히 들어간 후 미리보기 및 정보를 업데이트하기 위해 다음 틱에 스케줄링
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        gui.updatePreviewAndInfo(event.getInventory().getItem(StarforceGUI.ENHANCE_SLOT));
                    }, 1L);
                }
                // 강화 슬롯의 아이템을 플레이어 인벤토리로 꺼내려는 시도 (커서에 아이템이 없을 때)
                else if (clickedItem != null && !clickedItem.getType().isAir() && (cursorItem == null || cursorItem.getType().isAir())) {
                    event.setCancelled(false); // 아이템이 커서로 나오도록 허용
                    // 다음 틱에 미리보기 업데이트를 예약하여 슬롯이 비워진 후 처리
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        gui.updatePreviewAndInfo(null);
                    }, 1L);
                }
                // Shift + Click으로 아이템을 ENHANCE_SLOT으로 넣으려는 시도는 여기서 직접 처리되지 않습니다.
                // 이는 플레이어 인벤토리 영역에서의 클릭으로 처리됩니다.
            }
            // 1-2. 강화 버튼 (ENHANCE_BUTTON_SLOT) 클릭 처리
            else if (clickedSlot == StarforceGUI.ENHANCE_BUTTON_SLOT) {
                ItemStack itemToEnhance = gui.getInventory().getItem(StarforceGUI.ENHANCE_SLOT);

                if (itemToEnhance == null || itemToEnhance.getType().isAir()) {
                    player.sendMessage("§c[스타포스] 강화할 아이템을 강화 슬롯에 올려주세요.");
                    return;
                }

                int currentStars = StarforceDataUtil.getStars(itemToEnhance);
                if (currentStars >= StarforceDataUtil.MAX_STARFORCE_LEVEL) {
                    player.sendMessage("§c[스타포스] 이 아이템은 최대 강화 레벨에 도달하여 더 이상 강화할 수 없습니다.");
                    return;
                }

                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                    ItemStack resultItem = starforceManager.processStarforce(player, itemToEnhance);

                    Bukkit.getScheduler().runTask(plugin, () -> {
                        if (resultItem == null) {
                            gui.getInventory().setItem(StarforceGUI.ENHANCE_SLOT, null);
                            gui.getInventory().setItem(StarforceGUI.PREVIEW_SLOT, null);
                            player.closeInventory();
                        } else {
                            gui.getInventory().setItem(StarforceGUI.ENHANCE_SLOT, resultItem);
                            gui.updatePreviewAndInfo(resultItem);
                        }
                    });
                });
            } else if (clickedSlot == StarforceGUI.PREVIEW_SLOT ||
                    clickedSlot == StarforceGUI.CHANCE_INFO_SLOT ||
                    clickedSlot == StarforceGUI.COST_INFO_SLOT) {
                event.setCancelled(true);
            }
        }
        // 2. 플레이어 인벤토리 영역 (하단 인벤토리 영역) 클릭 처리
        else {
            // 플레이어 인벤토리 슬롯 (getRawSlot() >= gui.getInventory().getSize())은 기본적으로 상호작용 허용
            // event.setCancelled(false); // 이 부분은 이제 제거합니다.

            // --- 새로운 로직: 플레이어 인벤토리에서 Shift + Click 감지 ---
            // 클릭 타입이 SHIFT_LEFT 또는 SHIFT_RIGHT이고, 클릭된 아이템이 존재하는 경우
            if (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) {
                ItemStack clickedInPlayerInv = event.getCurrentItem(); // 플레이어 인벤토리에서 클릭된 아이템
                if (clickedInPlayerInv != null && !clickedInPlayerInv.getType().isAir()) {
                    // 아이템이 ENHANCE_SLOT으로 이동할 것인지 예측
                    // 이 시점에는 아이템이 아직 이동하기 전이므로, 슬롯에 들어갈 아이템을 예측해야 합니다.
                    // 간단하게, 강화 슬롯이 현재 비어있거나, 클릭된 아이템과 같은 종류의 아이템인지 확인하여
                    // 아이템이 강화 슬롯으로 이동할 것임을 가정합니다.
                    // 실제 이동은 Bukkit이 처리하고, 우리는 그 이후에 업데이트를 해야 합니다.

                    // 이벤트가 취소되지 않도록 허용하여 아이템이 이동하게 합니다.
                    event.setCancelled(false);

                    // 아이템 이동은 클릭 이벤트가 끝난 후에 발생하므로, 다음 틱에 스케줄링하여
                    // 아이템이 ENHANCE_SLOT에 실제로 들어간 후 updatePreviewAndInfo를 호출합니다.
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        ItemStack itemInEnhanceSlot = gui.getInventory().getItem(StarforceGUI.ENHANCE_SLOT);
                        if (itemInEnhanceSlot != null && !itemInEnhanceSlot.getType().isAir()) {
                            // 플레이어 인벤토리에서 쉬프트 클릭으로 아이템이 강화 슬롯으로 이동했을 경우
                            gui.updatePreviewAndInfo(itemInEnhanceSlot);
                        } else {
                            // 만약 강화 슬롯이 비었다면 (다른 곳으로 쉬프트 클릭되었거나, 아이템이 빠져나갔을 경우)
                            gui.updatePreviewAndInfo(null);
                        }
                    }, 1L); // 1틱 지연

                    return; // 더 이상 이 이벤트에 대해 다른 처리를 하지 않습니다.
                }
            }
            // -------------------------------------------------------------

            // 일반적인 플레이어 인벤토리 클릭은 허용합니다. (아이템 드래그/클릭 등)
            event.setCancelled(false);
        }
    }

    /**
     * 스타포스 GUI가 닫혔을 때 강화 슬롯에 남아있는 아이템을 플레이어에게 돌려줍니다.
     */
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getInventory().getHolder() instanceof StarforceGUI)) {
            return;
        }

        Player player = (Player) event.getPlayer();
        StarforceGUI gui = (StarforceGUI) event.getInventory().getHolder();

        ItemStack itemInSlot = gui.getInventory().getItem(StarforceGUI.ENHANCE_SLOT);
        if (itemInSlot != null && !itemInSlot.getType().isAir()) {
            player.getInventory().addItem(itemInSlot)
                    .forEach((index, item) -> player.getWorld().dropItemNaturally(player.getLocation(), item));
            gui.getInventory().setItem(StarforceGUI.ENHANCE_SLOT, null);
        }
    }
}
