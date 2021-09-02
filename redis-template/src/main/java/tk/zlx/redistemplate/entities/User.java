package tk.zlx.redistemplate.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "t_user" )
@Data
@Entity
public class User implements Serializable {

    @Id
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "addr")
    private String addr;
    @Column(name = "tel")
    private String tel;

}
