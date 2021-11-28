package kr.flab.fream.mybatis.mapper.user;

import java.util.List;
import kr.flab.fream.domain.user.model.Address;
import kr.flab.fream.domain.user.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * {@link User} 의 Mapper.
 *
 * @since 1.0.0
 */
@Mapper
public interface AddressMapper {
    int addAddress(Address address);

    int updateAddress(Address address);

    int deleteAddress(List<Address> addressBook);

    Address getAddress(Address address);

    List<Address> getAllAddress(User user);
}