package kr.flab.domain.product;

import static kr.flab.domain.product.BrandFixtures.getADIDAS;
import static kr.flab.domain.product.BrandFixtures.getJORDAN;
import static kr.flab.domain.product.BrandFixtures.getLEGO;
import static kr.flab.domain.product.BrandFixtures.getNewBalance;
import static kr.flab.domain.product.BrandFixtures.getNike;
import static kr.flab.domain.product.BrandFixtures.getSONY;
import static kr.flab.domain.product.BrandFixtures.getSUPREME;
import static kr.flab.domain.product.SizeFixtures.getClothingSizes;
import static kr.flab.domain.product.SizeFixtures.getOneSize;
import static kr.flab.domain.product.SizeFixtures.getSneakersSizes;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import kr.flab.fream.domain.product.model.Brand;
import kr.flab.fream.domain.product.model.Category;
import kr.flab.fream.domain.product.model.Product;
import kr.flab.fream.domain.product.model.ProductDetails;
import kr.flab.fream.domain.product.model.Sizes;

public class ProductFixtures {

    private static final Product NIKE_DUNK_LOW_RETRO_BLACK;
    private static final Product NIKE_OFF_WHITE_DUNK_LOT_18;
    private static final Product NIKE_PEACEMINUSONE_AIR_FORCE_1_LOW_PARA_NOISE_2;
    private static final Product NIKE_STUSSY_WINDRUNNER_JACKET_OFF_NOIR;
    private static final Product NIKE_STUSSY_BEACH_PANTS_OFF_NOIR;
    private static final Product NIKE_OFF_WHITE_NRG_PANTS_BLACK;

    private static final Product ADIDAS_YEEZY_BOOST_350_V2_BLACK_RED_2020;
    private static final Product ADIDAS_YEEZY_SLIDE_PURE;
    private static final Product ADIDAS_SUPERSTAR_PREMIUM_WHITE_BLACK;

    private static final Product NEW_BALANCE_992;

    private static final Product JORDAN_1_TRAVIS_SCOTT_RETRO_HIGH_OG_SP_MOCHA;
    private static final Product JORDAN_1_TRAVIS_SCOTT_FRAGMENT_RETRO_HIGH_OG_SP_MILITARY_BLUE;

    private static final Product SUPREME_WASHED_CHINO_TWILL_CAMP_CAP_BLACK;
    private static final Product SUPREME_CROSS_BOX_LOGO_HOODED_SWEATSHIRT_BLACK;
    private static final Product SUPREME_MESH_POCKET_BELTED_CARGO_PANTS_BLACK;

    private static final Product SONY_PLAYSTATION_5;

    private static final Product LEGO_DC_COMICS_BATMAN_THE_TUMBLER;

    static {
        NIKE_DUNK_LOW_RETRO_BLACK = new Product(1L, "나이키 덩크 로우 레트로 블랙", "Nike Dunk Low Retro Black",
                Category.SNEAKERS,
                new ProductDetails("DD1391-100", LocalDate.of(2021, 1, 14), 119000L),
                getNike(), SizeFixtures.getSneakersSizes());

        NIKE_OFF_WHITE_DUNK_LOT_18 = new Product(2L, "나이키 x 오프화이트 덩크 로우 더 50 - 로트 18",
                "Nike x Off-White Dunk Low The 50 - Lot 18",
                Category.SNEAKERS,
                new ProductDetails("DJ0950-112", LocalDate.of(2021, 8, 9), 219000L), getNike(),
                SizeFixtures.getSneakersSizes());

        NIKE_PEACEMINUSONE_AIR_FORCE_1_LOW_PARA_NOISE_2 = new Product(3L,
                "나이키 x 피스마이너스원 에어포스 1 로우 파라노이즈 2.0",
                "Nike x Peaceminusone Air Force 1 Low Para-Noise 2.0",
                Category.SNEAKERS,
                new ProductDetails("DD3223-100", LocalDate.of(2020, 11, 25), 219_000L),
                getNike(),
                SizeFixtures.getSneakersSizes());

        NIKE_STUSSY_WINDRUNNER_JACKET_OFF_NOIR = new Product(4L,
                "나이키 x 스투시 윈드러너 자켓 오프 느와르", "Nike x Stussy Windrunner Jacket Off Noir",
                Category.OUTER,
                new ProductDetails("CT4310-045", null, 229_000L), getNike(), getClothingSizes());

        NIKE_STUSSY_BEACH_PANTS_OFF_NOIR = new Product(5L,
                "나이키 x 스투시 비치팬츠 오프 느와르", "Nike x Stussy Beach Pants Off Noir",
                Category.BOTTOM,
                new ProductDetails("CT4316-045", null, 169_000L),
                getNike(), getClothingSizes());

        NIKE_OFF_WHITE_NRG_PANTS_BLACK = new Product(6L, "나이키 x 오프화이트 NRG 팬츠 블랙",
                "Nike x Off-White NRG Pants Black",
                Category.BOTTOM,
                new ProductDetails("CU2500-010", LocalDate.of(2021, 7, 23), 279_000L), getNike(),
                getClothingSizes());

        ADIDAS_YEEZY_BOOST_350_V2_BLACK_RED_2020 = new Product(51L,
                "아디다스 이지 부스트 350 V2 블랙 레드 2020", "Adidas Yeezy Boost 350 V2 Black Red 2020",
                Category.SNEAKERS,
                new ProductDetails("CP9652", LocalDate.of(2020, 12, 5), 289_000L), getADIDAS(),
                getSneakersSizes());

        ADIDAS_YEEZY_SLIDE_PURE = new Product(52L,
                "아디다스 이지 슬라이드 퓨어", "Adidas Yeezy Slide Pure", Category.SNEAKERS,
                new ProductDetails("GZ5554", LocalDate.of(2021, 4, 26), 289_000L), getADIDAS(),
                getSneakersSizes());

        ADIDAS_SUPERSTAR_PREMIUM_WHITE_BLACK = new Product(53L, "아디다스 슈퍼스타 프리미엄 화이트 블랙",
                "Adidas Superstar Premium White Black",
                Category.SNEAKERS,
                new ProductDetails("FV2831", LocalDate.of(2020, 1, 1), 116_600L),
                getADIDAS(), getSneakersSizes());

        NEW_BALANCE_992 = new Product(101L, "뉴발란스 992 메이드 인 USA 그레이 (D 스탠다드)",
                "New Balance 992 Made in USA Grey (D Standard)",
                Category.SNEAKERS,
                new ProductDetails("M992GR", LocalDate.of(2020, 4, 13), 259_000L),
                getNewBalance(), getSneakersSizes());

        JORDAN_1_TRAVIS_SCOTT_RETRO_HIGH_OG_SP_MOCHA = new Product(151L,
                "조던 1 x 트래비스 스캇 레트로 하이 OG SP 모카", "Jordan 1 x Travis Scott Retro High OG SP Mocha",
                Category.SNEAKERS,
                new ProductDetails("CD4487-100", LocalDate.of(2019, 5, 12), 239_000L),
                getJORDAN(), getSneakersSizes());

        JORDAN_1_TRAVIS_SCOTT_FRAGMENT_RETRO_HIGH_OG_SP_MILITARY_BLUE = new Product(152L,
                "조던 1 x 트래비스 스캇 x 프라그먼트 레트로 하이 OG SP 밀리터리 블루",
                "Jordan 1 x Travis Scott x Fragment Retro High OG SP Military Blue",
                Category.SNEAKERS,
                new ProductDetails("DH3227-105", LocalDate.of(2021, 7, 29), 239_000L),
                getJORDAN(), getSneakersSizes());

        SUPREME_WASHED_CHINO_TWILL_CAMP_CAP_BLACK = new Product(201L,
                "슈프림 워시드 치노 트윌 캠프캡 블랙", "Supreme Washed Chino Twill Camp Cap Black",
                Category.HAT,
                new ProductDetails(null, null, 55_900L), getSUPREME(), getOneSize());

        SUPREME_CROSS_BOX_LOGO_HOODED_SWEATSHIRT_BLACK = new Product(202L,
                "슈프림 크로스 박스로고 후드 스웨트셔츠 블랙 (20FW)",
                "Supreme Cross Box Logo Hooded Sweatshirt Black (20FW)",
                Category.TOP,
                new ProductDetails(null, LocalDate.of(2020, 12, 3), 195_900L),
                getSUPREME(), getClothingSizes());

        SUPREME_MESH_POCKET_BELTED_CARGO_PANTS_BLACK = new Product(203L,
                "슈프림 메쉬 포켓 벨티드 카고 팬츠 블랙 (21SS)",
                "Supreme Mesh Pocket Belted Cargo Pants Black (21SS)",
                Category.BOTTOM,
                new ProductDetails(null, LocalDate.of(2021, 6, 10), 234_900L),
                getSUPREME(), getClothingSizes());

        SONY_PLAYSTATION_5 = new Product(251L,
                "소니 플레이스테이션 5 디지털 에디션 (한국 정식 발매 제품)",
                "Sony Playstation 5 Digital Edition (SIEK 220V)",
                Category.TECH,
                new ProductDetails(null, LocalDate.of(2020, 11, 12), 498_000L),
                getSONY(), getOneSize());

        LEGO_DC_COMICS_BATMAN_THE_TUMBLER = new Product(301L,
                "레고 DC 코믹스 배트맨 텀블러", "Lego DC Comics Batman The Tumbler",
                Category.LIFE,
                new ProductDetails("76023", LocalDate.of(2014, 12, 24), 233200L),
                getLEGO(), getOneSize());

    }

    private ProductFixtures() {
    }

    public static Product getNikeDunkLowRetroBlack() {
        return NIKE_DUNK_LOW_RETRO_BLACK;
    }

    public static Product getNikeOffWhiteDunkLot18() {
        return NIKE_OFF_WHITE_DUNK_LOT_18;
    }

    public static Product getNikePeaceminusoneAirForce1LowParaNoise2() {
        return NIKE_PEACEMINUSONE_AIR_FORCE_1_LOW_PARA_NOISE_2;
    }

    public static Product getNikeStussyWindrunnerJacketOffNoir() {
        return NIKE_STUSSY_WINDRUNNER_JACKET_OFF_NOIR;
    }

    public static Product getNikeStussyBeachPantsOffNoir() {
        return NIKE_STUSSY_BEACH_PANTS_OFF_NOIR;
    }

    public static Product getNikeOffWhiteNrgPantsBlack() {
        return NIKE_OFF_WHITE_NRG_PANTS_BLACK;
    }

    public static Product getAdidasYeezyBoost350V2BlackRed2020() {
        return ADIDAS_YEEZY_BOOST_350_V2_BLACK_RED_2020;
    }

    public static Product getAdidasYeezySlidePure() {
        return ADIDAS_YEEZY_SLIDE_PURE;
    }

    public static Product getAdidasSuperstarPremiumWhiteBlack() {
        return ADIDAS_SUPERSTAR_PREMIUM_WHITE_BLACK;
    }

    public static Product getNewBalance992() {
        return NEW_BALANCE_992;
    }

    public static Product getJordan1TravisScottRetroHighOgSpMocha() {
        return JORDAN_1_TRAVIS_SCOTT_RETRO_HIGH_OG_SP_MOCHA;
    }

    public static Product getJordan1TravisScottFragmentRetroHighOgSpMilitaryBlue() {
        return JORDAN_1_TRAVIS_SCOTT_FRAGMENT_RETRO_HIGH_OG_SP_MILITARY_BLUE;
    }

    public static Product getSupremeWashedChinoTwillCampCapBlack() {
        return SUPREME_WASHED_CHINO_TWILL_CAMP_CAP_BLACK;
    }

    public static Product getSupremeCrossBoxLogoHoodedSweatshirtBlack() {
        return SUPREME_CROSS_BOX_LOGO_HOODED_SWEATSHIRT_BLACK;
    }

    public static Product getSupremeMeshPocketBeltedCargoPantsBlack() {
        return SUPREME_MESH_POCKET_BELTED_CARGO_PANTS_BLACK;
    }

    public static Product getSonyPlaystation5() {
        return SONY_PLAYSTATION_5;
    }

    public static Product getLegoDcComicsBatmanTheTumbler() {
        return LEGO_DC_COMICS_BATMAN_THE_TUMBLER;
    }

    public static List<Product> allProducts() {
        return Arrays.asList(
                NIKE_DUNK_LOW_RETRO_BLACK,
                NIKE_OFF_WHITE_DUNK_LOT_18,
                NIKE_PEACEMINUSONE_AIR_FORCE_1_LOW_PARA_NOISE_2,
                NIKE_STUSSY_WINDRUNNER_JACKET_OFF_NOIR,
                NIKE_STUSSY_BEACH_PANTS_OFF_NOIR,
                NIKE_OFF_WHITE_NRG_PANTS_BLACK,
                ADIDAS_YEEZY_BOOST_350_V2_BLACK_RED_2020,
                ADIDAS_YEEZY_SLIDE_PURE,
                ADIDAS_SUPERSTAR_PREMIUM_WHITE_BLACK,
                NEW_BALANCE_992,
                JORDAN_1_TRAVIS_SCOTT_RETRO_HIGH_OG_SP_MOCHA,
                JORDAN_1_TRAVIS_SCOTT_FRAGMENT_RETRO_HIGH_OG_SP_MILITARY_BLUE,
                SUPREME_WASHED_CHINO_TWILL_CAMP_CAP_BLACK,
                SUPREME_CROSS_BOX_LOGO_HOODED_SWEATSHIRT_BLACK,
                SUPREME_MESH_POCKET_BELTED_CARGO_PANTS_BLACK,
                SONY_PLAYSTATION_5,
                LEGO_DC_COMICS_BATMAN_THE_TUMBLER
        );
    }

    public static List<Product> getNikeProducts() {
        return Arrays.asList(
                NIKE_DUNK_LOW_RETRO_BLACK,
                NIKE_OFF_WHITE_DUNK_LOT_18,
                NIKE_PEACEMINUSONE_AIR_FORCE_1_LOW_PARA_NOISE_2,
                NIKE_STUSSY_WINDRUNNER_JACKET_OFF_NOIR,
                NIKE_STUSSY_BEACH_PANTS_OFF_NOIR,
                NIKE_OFF_WHITE_NRG_PANTS_BLACK
        );
    }

    public static List<Product> getAdidasProducts() {
        return Arrays.asList(
                ADIDAS_YEEZY_BOOST_350_V2_BLACK_RED_2020,
                ADIDAS_YEEZY_SLIDE_PURE,
                ADIDAS_SUPERSTAR_PREMIUM_WHITE_BLACK
        );
    }

    public static List<Product> getClothes() {
        return Arrays.asList(
                NIKE_STUSSY_BEACH_PANTS_OFF_NOIR,
                NIKE_STUSSY_WINDRUNNER_JACKET_OFF_NOIR,
                NIKE_OFF_WHITE_NRG_PANTS_BLACK,
                SUPREME_CROSS_BOX_LOGO_HOODED_SWEATSHIRT_BLACK
        );
    }

    public static Product createSneakers(Brand brand, Sizes sizes) {
        final var details = new ProductDetails("ABCD-1234", LocalDate.of(2021, Month.AUGUST, 28),
                239_000L);
        return new Product(null, "신발", "sneakers", Category.SNEAKERS, details, brand, sizes);
    }

}
