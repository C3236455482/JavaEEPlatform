//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.javaee.rediscachetest.dao.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Long id;
    private String name;
}
