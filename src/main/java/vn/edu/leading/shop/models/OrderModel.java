package vn.edu.leading.shop.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "shop_orders")
public class OrderModel extends BaseModel<OrderModel> {

    @Column(name = "order_date")
    private String orderDate;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @BatchSize(size = 50)
    private UserModel userModel;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "shipper_id", nullable = false)
    @BatchSize(size = 50)
    private ShipperModel shipperModel;

    @OneToMany(
            mappedBy = "orderModel",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 50)
    private List<OrderDetailModel> orderDetails = new ArrayList<>();
}
