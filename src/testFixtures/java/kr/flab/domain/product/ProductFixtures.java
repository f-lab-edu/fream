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
import java.util.Objects;
import java.util.stream.Stream;
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
        NIKE_DUNK_LOW_RETRO_BLACK = new Product(1L, "????????? ?????? ?????? ????????? ??????", "Nike Dunk Low Retro Black",
                Category.SNEAKERS,
                new ProductDetails("DD1391-100", LocalDate.of(2021, 1, 14), 119000L),
                getNike(), SizeFixtures.getSneakersSizes(), 1L);

        NIKE_OFF_WHITE_DUNK_LOT_18 = new Product(2L, "????????? x ??????????????? ?????? ?????? ??? 50 - ?????? 18",
                "Nike x Off-White Dunk Low The 50 - Lot 18",
                Category.SNEAKERS,
                new ProductDetails("DJ0950-112", LocalDate.of(2021, 8, 9), 219000L), getNike(),
                SizeFixtures.getSneakersSizes(), 2L);

        NIKE_PEACEMINUSONE_AIR_FORCE_1_LOW_PARA_NOISE_2 = new Product(3L,
                "????????? x ????????????????????? ???????????? 1 ?????? ??????????????? 2.0",
                "Nike x Peaceminusone Air Force 1 Low Para-Noise 2.0",
                Category.SNEAKERS,
                new ProductDetails("DD3223-100", LocalDate.of(2020, 11, 25), 219_000L),
                getNike(),
                SizeFixtures.getSneakersSizes(), 3L);

        NIKE_STUSSY_WINDRUNNER_JACKET_OFF_NOIR = new Product(4L,
                "????????? x ????????? ???????????? ?????? ?????? ?????????", "Nike x Stussy Windrunner Jacket Off Noir",
                Category.OUTER,
                new ProductDetails("CT4310-045", null, 229_000L), getNike(), getClothingSizes(),
                4L);

        NIKE_STUSSY_BEACH_PANTS_OFF_NOIR = new Product(5L,
                "????????? x ????????? ???????????? ?????? ?????????", "Nike x Stussy Beach Pants Off Noir",
                Category.BOTTOM,
                new ProductDetails("CT4316-045", null, 169_000L),
                getNike(), getClothingSizes(), 5L);

        NIKE_OFF_WHITE_NRG_PANTS_BLACK = new Product(6L, "????????? x ??????????????? NRG ?????? ??????",
                "Nike x Off-White NRG Pants Black",
                Category.BOTTOM,
                new ProductDetails("CU2500-010", LocalDate.of(2021, 7, 23), 279_000L), getNike(),
                getClothingSizes(), 6L);

        ADIDAS_YEEZY_BOOST_350_V2_BLACK_RED_2020 = new Product(51L,
                "???????????? ?????? ????????? 350 V2 ?????? ?????? 2020", "Adidas Yeezy Boost 350 V2 Black Red 2020",
                Category.SNEAKERS,
                new ProductDetails("CP9652", LocalDate.of(2020, 12, 5), 289_000L), getADIDAS(),
                getSneakersSizes(), 51L);

        ADIDAS_YEEZY_SLIDE_PURE = new Product(52L,
                "???????????? ?????? ???????????? ??????", "Adidas Yeezy Slide Pure", Category.SNEAKERS,
                new ProductDetails("GZ5554", LocalDate.of(2021, 4, 26), 289_000L), getADIDAS(),
                getSneakersSizes(), 52L);

        ADIDAS_SUPERSTAR_PREMIUM_WHITE_BLACK = new Product(53L, "???????????? ???????????? ???????????? ????????? ??????",
                "Adidas Superstar Premium White Black",
                Category.SNEAKERS,
                new ProductDetails("FV2831", LocalDate.of(2020, 1, 1), 116_600L),
                getADIDAS(), getSneakersSizes(), 53L);

        NEW_BALANCE_992 = new Product(101L, "???????????? 992 ????????? ??? USA ????????? (D ????????????)",
                "New Balance 992 Made in USA Grey (D Standard)",
                Category.SNEAKERS,
                new ProductDetails("M992GR", LocalDate.of(2020, 4, 13), 259_000L),
                getNewBalance(), getSneakersSizes(), 101L);

        JORDAN_1_TRAVIS_SCOTT_RETRO_HIGH_OG_SP_MOCHA = new Product(151L,
                "?????? 1 x ???????????? ?????? ????????? ?????? OG SP ??????", "Jordan 1 x Travis Scott Retro High OG SP Mocha",
                Category.SNEAKERS,
                new ProductDetails("CD4487-100", LocalDate.of(2019, 5, 12), 239_000L),
                getJORDAN(), getSneakersSizes(), 151L);

        JORDAN_1_TRAVIS_SCOTT_FRAGMENT_RETRO_HIGH_OG_SP_MILITARY_BLUE = new Product(152L,
                "?????? 1 x ???????????? ?????? x ??????????????? ????????? ?????? OG SP ???????????? ??????",
                "Jordan 1 x Travis Scott x Fragment Retro High OG SP Military Blue",
                Category.SNEAKERS,
                new ProductDetails("DH3227-105", LocalDate.of(2021, 7, 29), 239_000L),
                getJORDAN(), getSneakersSizes(), 152L);

        SUPREME_WASHED_CHINO_TWILL_CAMP_CAP_BLACK = new Product(201L,
                "????????? ????????? ?????? ?????? ????????? ??????", "Supreme Washed Chino Twill Camp Cap Black",
                Category.HAT,
                new ProductDetails(null, null, 55_900L), getSUPREME(), getOneSize(), 201L);

        SUPREME_CROSS_BOX_LOGO_HOODED_SWEATSHIRT_BLACK = new Product(202L,
                "????????? ????????? ???????????? ?????? ??????????????? ?????? (20FW)",
                "Supreme Cross Box Logo Hooded Sweatshirt Black (20FW)",
                Category.TOP,
                new ProductDetails(null, LocalDate.of(2020, 12, 3), 195_900L),
                getSUPREME(), getClothingSizes(), 202L);

        SUPREME_MESH_POCKET_BELTED_CARGO_PANTS_BLACK = new Product(203L,
                "????????? ?????? ?????? ????????? ?????? ?????? ?????? (21SS)",
                "Supreme Mesh Pocket Belted Cargo Pants Black (21SS)",
                Category.BOTTOM,
                new ProductDetails(null, LocalDate.of(2021, 6, 10), 234_900L),
                getSUPREME(), getClothingSizes(), 203L);

        SONY_PLAYSTATION_5 = new Product(251L,
                "?????? ????????????????????? 5 ????????? ????????? (?????? ?????? ?????? ??????)",
                "Sony Playstation 5 Digital Edition (SIEK 220V)",
                Category.GAMING_CONSOLE,
                new ProductDetails(null, LocalDate.of(2020, 11, 12), 498_000L),
                getSONY(), getOneSize(), 251L);

        LEGO_DC_COMICS_BATMAN_THE_TUMBLER = new Product(301L,
                "?????? DC ????????? ????????? ?????????", "Lego DC Comics Batman The Tumbler",
                Category.LIFE,
                new ProductDetails("76023", LocalDate.of(2014, 12, 24), 233200L),
                getLEGO(), getOneSize(), 301L);

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
                SUPREME_CROSS_BOX_LOGO_HOODED_SWEATSHIRT_BLACK,
                SUPREME_MESH_POCKET_BELTED_CARGO_PANTS_BLACK
        );
    }

    public static Product createSneakers(Brand brand, Sizes sizes) {
        final var details = new ProductDetails("ABCD-1234", LocalDate.of(2021, Month.AUGUST, 28),
                239_000L);
        return new Product(null, "??????", "sneakers", Category.SNEAKERS, details, brand, sizes, 0L);
    }

    public static Stream<Product> sortByViewCount(Stream<Product> s) {
        return s.sorted((a, b) -> -1 * (a.getViewCount().compareTo(b.getViewCount())));
    }

    public static Stream<Product> sortByReleaseDate(Stream<Product> s) {
        return s.sorted((a, b) -> {
            LocalDate releaseDate1 = a.getDetails().getReleaseDate();
            LocalDate releaseDate2 = b.getDetails().getReleaseDate();

            if (Objects.equals(releaseDate1, releaseDate2)) {
                return 0;
            }
            if (releaseDate1 == null) {
                return 1;
            }
            if (releaseDate2 == null) {
                return -1;
            }
            return -1 * (releaseDate1.compareTo(releaseDate2));
        });
    }


}
