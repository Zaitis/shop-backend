package pl.zaitis.shop.admin.category.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="category")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", nullable = false)
    private Long id;
    private String name;
    private String description;
    private String slug;
}
