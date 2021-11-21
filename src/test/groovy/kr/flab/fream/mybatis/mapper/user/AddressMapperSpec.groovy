package kr.flab.fream.mybatis.mapper.user

import kr.flab.fream.domain.user.model.Address

import kr.flab.fream.domain.user.model.User
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import spock.lang.Specification
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AddressMapperSpec extends Specification {
    @Autowired
    AddressMapper addressMapper;
    def "add address"() {
        given:
        Address address = new Address();
        address.setUserId(1);
        address.setAddress("서울");
        address.setAddressDetail("광진구");
        address.setAlias("집");
        address.setIsDefault(true);

        expect:
        addressMapper.addAddress(address) == 1
    }

    def "update address"() {
        given:
        def address = new Address();
        address.setId(2);
        address.setIsDefault(true);

        expect:
        addressMapper.updateAddress(address) == 1;
    }
    def "get address"() {
        given:
        Address address = new Address();
        address.setId(2);

        expect:
        addressMapper.getAddress(address).getAddress()
                == "일본"
    }

    def "delete address"() {
        given:
        def user = new User();
        user.setId(1);
        List<Address> addressBook = new ArrayList<>();
        for(Address address in addressMapper.getAllAddress(user)){
            addressBook.add(address);
        }
        user.setAddressBook(addressBook);

        expect:
        addressMapper.deleteAddress(user.getAddressBook()) == 3
    }
    def "get All address"() {
        given:
        def user = new User(1);

        expect:
        addressMapper.getAllAddress(user).size() == 3
    }
}
