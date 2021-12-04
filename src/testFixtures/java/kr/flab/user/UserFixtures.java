package kr.flab.user;

import java.util.Collections;
import kr.flab.fream.domain.user.model.Address;
import kr.flab.fream.domain.user.model.User;

public class UserFixtures {

    private UserFixtures() {
    }

    public static User fream() {
        Address address = new Address(100_000L, 100_000L, "addr1", "address", "address details",
                true);
        return new User(100_000L, "password", "fream", Collections.singletonList(address),
                "fream@flab.kr", "010-1234-5678", "account");
    }

    public static User tester() {
        Address address = new Address(100_001L, 100_001L, "addr2", "address", "address details",
                true);
        return new User(100_001L, "password", "tester", Collections.singletonList(address),
                "tester@flab.kr", "010-0987-6543", "account2");
    }

}
