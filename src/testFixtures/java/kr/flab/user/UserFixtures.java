package kr.flab.user;

import java.util.Collections;
import kr.flab.fream.domain.user.model.Address;
import kr.flab.fream.domain.user.model.User;

public class UserFixtures {

    private UserFixtures() {
    }

    public static User firstUser() {
        Address address = new Address(1L, 1L, "addr1", "address", "address details",
                true, null, null);
        return new User(1L, "password", "user", Collections.singletonList(address),
                "user@flab.kr", "010-1234-5678", "account", null, null);
    }

    public static User fream() {
        Address address = new Address(100_000L, 100_000L, "addr1", "address", "address details",
                true, null, null);
        return new User(100_000L, "password", "fream", Collections.singletonList(address),
                "fream@flab.kr", "010-1234-5678", "account", null, null);
    }

    public static User tester() {
        Address address = new Address(100_001L, 100_001L, "addr2", "address", "address details",
                true, null, null);
        return new User(100_001L, "password", "tester", Collections.singletonList(address),
                "tester@flab.kr", "010-0987-6543", "account2", null, null);
    }

}
