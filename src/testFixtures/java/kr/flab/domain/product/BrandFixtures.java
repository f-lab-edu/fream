package kr.flab.domain.product;

import kr.flab.fream.domain.product.model.Brand;

public class BrandFixtures {

    private static final Brand NIKE;
    private static final Brand ADIDAS;
    private static final Brand NEW_BALANCE;
    private static final Brand CONVERSE;
    private static final Brand JORDAN;
    private static final Brand SUPREME;
    private static final Brand PALACE;
    private static final Brand STUSSY;
    private static final Brand IAB_STUDIO;
    private static final Brand SONY;
    private static final Brand SAMSUNG;
    private static final Brand NINTENDO;
    private static final Brand NVIDIA;
    private static final Brand LEGO;

    static {
        NIKE = new Brand(1L, "나이키", "Nike");
        ADIDAS = new Brand(2L, "아디다스", "Adidas");
        NEW_BALANCE = new Brand(3L, "뉴발란스", "New Balance");
        CONVERSE = new Brand(4L, "컨버스", "Converse");
        JORDAN = new Brand(5L, "조던", "Jordan");
        SUPREME = new Brand(6L, "슈프림", "Supreme");
        PALACE = new Brand(7L, "팔라스", "Palace");
        STUSSY = new Brand(8L, "스투시", "Stussy");
        IAB_STUDIO = new Brand(9L, "아이앱 스튜디오", "IAB Studio");
        SONY = new Brand(10L, "소니", "Sony");
        SAMSUNG = new Brand(11L, "삼성", "Samsung");
        NINTENDO = new Brand(12L, "닌텐도", "Nintendo");
        NVIDIA = new Brand(13L, "엔비디아", "Nvidia");
        LEGO = new Brand(14L, "레고", "Lego");
    }

    private BrandFixtures() {
    }

    public static Brand getNike() {
        return NIKE;
    }

    public static Brand getADIDAS() {
        return ADIDAS;
    }

    public static Brand getNewBalance() {
        return NEW_BALANCE;
    }

    public static Brand getCONVERSE() {
        return CONVERSE;
    }

    public static Brand getJORDAN() {
        return JORDAN;
    }

    public static Brand getSUPREME() {
        return SUPREME;
    }

    public static Brand getPALACE() {
        return PALACE;
    }

    public static Brand getSTUSSY() {
        return STUSSY;
    }

    public static Brand getIabStudio() {
        return IAB_STUDIO;
    }

    public static Brand getSONY() {
        return SONY;
    }

    public static Brand getSAMSUNG() {
        return SAMSUNG;
    }

    public static Brand getNINTENDO() {
        return NINTENDO;
    }

    public static Brand getNVIDIA() {
        return NVIDIA;
    }

    public static Brand getLEGO() {
        return LEGO;
    }

    public static Brand createBrand() {
        return new Brand(null, "brand", "brand");
    }

}
