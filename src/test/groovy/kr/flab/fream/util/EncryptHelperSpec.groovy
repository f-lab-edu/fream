package kr.flab.fream.util

import kr.flab.fream.util.EncryptHelper
import spock.lang.Specification

class EncryptHelperSpec extends Specification {

    def "hash salt test"() {
        given:
        EncryptHelper encryptHelper = new BcryptHelper();

        when:
        String hashStr1 = encryptHelper.encryptPassword("1234");
        String hashStr2 = encryptHelper.encryptPassword("1234");
        then:
        hashStr1 != hashStr2
    }
}
