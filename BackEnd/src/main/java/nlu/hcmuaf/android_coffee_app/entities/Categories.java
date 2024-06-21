package nlu.hcmuaf.android_coffee_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlu.hcmuaf.android_coffee_app.enums.ECategory;
import nlu.hcmuaf.android_coffee_app.enums.EProductType;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "categories")
public class Categories implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId")
    private long categoryId;

    @Column(name = "categoryName")
    private String categoryName;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "active")
    private boolean active;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private EProductType type;

    @Column(name = "categoryEnum")
    @Enumerated(EnumType.STRING)
    private ECategory categoryEnum;
}
