package pl.zaitis.shop.admin.order.service;

import pl.zaitis.shop.admin.order.model.AdminOrderStatus;

public class AdminOrderEmailMessage {
    public static String createProcessingEmailMessage(Long id, AdminOrderStatus newStatus) {
        return "Your order: " + id + " is processing." +
                "\n Status was be change to: " + newStatus.getValue() +"." +
                "\n Your order is processing by our employee." +
                "\n Where we end. Your order will be send immediately." +
                "\n\n Greetings," +
                "\n Shop Team";
    }

    public static String createCompletedEmailMessage(Long id, AdminOrderStatus newStatus) {
        return "Your order: " + id + " is completed." +
                "\n Status was be change to: " + newStatus.getValue() +
                "\n Your order is processing by our employee." +
                "\n Thank you for order in our shop." +
                "\n\n Greetings," +
                "\n Shop Team";
    }

    public static String createRefundEmailMessage(Long id, AdminOrderStatus newStatus) {
        return "Your order: " + id + " was refund." +
                "\n Status was be change to: " + newStatus.getValue() +
                "\n Your order is processing by our employee." +
                "\n\n Greetings," +
                "\n Shop Team";
    }
}
