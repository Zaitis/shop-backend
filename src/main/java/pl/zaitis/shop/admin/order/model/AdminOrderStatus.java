package pl.zaitis.shop.admin.order.model;

public enum AdminOrderStatus {
    NEW("New"),
    PAID("Paid"),
    PROCESSING("Processing"),
    WAITING_FOR_DELIVERY("Waiting for delivery"),
    COMPLETED("Completed"),
    CANCELED("Canceled"),
    REFUND("Refund");


    private String value;

    AdminOrderStatus(String value){
        this.value= value;
    }

    public String getValue(){
        return value;
    }
}
