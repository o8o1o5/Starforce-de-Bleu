package dev.o8o1o5.starforceDeBleu.data;

public enum StarforceLevel {
    // 별 개수 0부터 순서대로 정의합니다.
    // 각 상수의 인자 순서: 성공률, 실패율, 파괴율 (모두 합쳐 100이 되어야 함)

    // --- 0 ~ 5성: 비교적 쉬움 (파괴 없음) ---
    LEVEL_0(100, 0, 0),
    LEVEL_1(95, 5, 0),
    LEVEL_2(90, 10, 0),
    LEVEL_3(85, 15, 0),
    LEVEL_4(80, 20, 0),
    LEVEL_5(75, 25, 0),

    // --- 6 ~ 10성: 약간 어려워짐 (파괴 없음 유지, 실패율 증가) ---
    LEVEL_6(65, 35, 0),
    LEVEL_7(55, 45, 0),
    LEVEL_8(45, 55, 0),
    LEVEL_9(35, 65, 0),
    LEVEL_10(30, 70, 0),

    // --- 11 ~ 15성: 크게 어려워짐 (파괴 확률 도입, 한 자리 수 유지) ---
    LEVEL_11(25, 70, 5), // 성공 25%, 실패 70%, 파괴 5%
    LEVEL_12(20, 75, 5),
    LEVEL_13(15, 80, 5),
    LEVEL_14(10, 85, 5),
    LEVEL_15(8, 87, 5),

    // --- 16 ~ 18성: 더욱 어려워지지만 파괴 확률 한 자리 수 유지 ---
    LEVEL_16(7, 85, 8), // 성공 7%, 실패 85%, 파괴 8%
    LEVEL_17(6, 85, 9),
    LEVEL_18(5, 85, 10), // 18성에서 파괴 확률이 10%로 처음 두 자리 수가 됩니다.

    // --- 19 ~ 25성: 매우 어려워짐 (파괴 확률 본격적 증가) ---
    LEVEL_19(4, 75, 21), // 예시: 성공 4%, 실패 75%, 파괴 21%
    LEVEL_20(3, 70, 27),
    LEVEL_21(2, 65, 33),
    LEVEL_22(2, 60, 38),
    LEVEL_23(1, 55, 44),
    LEVEL_24(1, 50, 49),
    LEVEL_25(1, 45, 54)
    ;

    // private final Material displayMaterial; // 이 필드를 제거합니다.
    private final int successRate;
    private final int failRate;
    private final int destroyRate;

    // 생성자에서 displayMaterial 인자를 제거합니다.
    StarforceLevel(int successRate, int failRate, int destroyRate) {
        this.successRate = successRate;
        this.failRate = failRate;
        this.destroyRate = destroyRate;
    }

    // getDisplayMaterial() 메서드도 제거합니다.
    // public Material getDisplayMaterial() {
    //     return displayMaterial;
    // }

    public int getSuccessRate() {
        return successRate;
    }

    public int getFailRate() {
        return failRate;
    }

    public int getDestroyRate() {
        return destroyRate;
    }

    /**
     * 주어진 별 개수에 해당하는 StarforceLevel enum을 반환합니다.
     * 만약 starCount가 정의된 레벨 범위를 벗어나면, 가장 가까운 유효한 레벨을 반환합니다.
     * (예: 음수이면 0성, 정의된 최대 레벨을 초과하면 최대 레벨)
     *
     * @param starCount 현재 아이템의 별 개수
     * @return 해당 별 개수에 대한 StarforceLevel enum
     */
    public static StarforceLevel getLevel(int starCount) {
        StarforceLevel[] levels = values(); // 모든 StarforceLevel enum 상수 배열

        // 별 개수가 0 미만인 경우 (예외 처리)
        if (starCount < 0) {
            return levels[0]; // 0성 (LEVEL_0)으로 처리
        }
        // 별 개수가 정의된 최대 레벨을 초과하는 경우
        if (starCount >= levels.length) {
            return levels[levels.length - 1]; // 정의된 가장 높은 레벨로 처리
        }
        // 유효한 범위 내의 별 개수인 경우
        return levels[starCount];
    }
}
