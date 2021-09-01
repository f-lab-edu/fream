package kr.flab.fream.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import kr.flab.fream.domain.product.Category;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * Mybatis 가 {@link Category} enum 을 매핑할 때 사용하는 핸들러.
 *
 * @since 1.0.0
 */
public class CategoryTypeHandler implements TypeHandler<Category> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Category parameter, JdbcType jdbcType)
                throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public Category getResult(ResultSet rs, String columnName) throws SQLException {
        final var categoryString = rs.getString(columnName);
        return Category.of(categoryString);
    }

    @Override
    public Category getResult(ResultSet rs, int columnIndex) throws SQLException {
        final var categoryString = rs.getString(columnIndex);
        return Category.of(categoryString);
    }

    @Override
    public Category getResult(CallableStatement cs, int columnIndex) throws SQLException {
        final var categoryString = cs.getString(columnIndex);
        return Category.of(categoryString);
    }
}
