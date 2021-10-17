package kr.flab.domain.product;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import kr.flab.fream.domain.product.model.Size;
import kr.flab.fream.domain.product.model.Sizes;

public class SizeFixtures {

    private static final Sizes SNEAKERS_SIZES;
    private static final Sizes CLOTHING_SIZES;

    private static final Sizes ONE_SIZE;

    static {
        List<Integer> sizeList = IntStream.iterate(220, size -> size <= 300, size -> size + 5)
                .boxed()
                .collect(Collectors.toList());

        sizeList.add(310);
        sizeList.add(320);

        SNEAKERS_SIZES = IntStream.range(0, sizeList.size())
                .mapToObj(i -> new Size(i + 1L, String.valueOf(sizeList.get(i))))
                .collect(Collectors.collectingAndThen(Collectors.toList(), Sizes::new));

        CLOTHING_SIZES = new Sizes(
                Arrays.asList(
                        new Size(20L, "XS"),
                        new Size(21L, "S"),
                        new Size(22L, "M"),
                        new Size(23L, "L"),
                        new Size(24L, "XL"),
                        new Size(25L, "XXL"),
                        new Size(26L, "XXXL"),
                        new Size(27L, "XXXXL")
                )
        );

        ONE_SIZE = new Sizes(Collections.singletonList(new Size(30L, "ONE SIZE")));
    }

    private SizeFixtures() {
    }

    public static Sizes getSneakersSizes() {
        return SNEAKERS_SIZES;
    }

    public static Sizes getClothingSizes() {
        return CLOTHING_SIZES;
    }

    public static Sizes getOneSize() {
        return ONE_SIZE;
    }

    public static Size createUs8Size() {
        return new Size(null, "US 8.0");
    }

    public static Size createUs85Size() {
        return new Size(null, "US 8.5");
    }


}
