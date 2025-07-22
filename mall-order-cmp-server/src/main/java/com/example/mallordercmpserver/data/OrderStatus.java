package com.example.mallordercmpserver.data;

/**
 * 订单状态枚举
 */
public enum OrderStatus {
    /**
     * 待付款 (0)
     */
    PENDING(0, "待付款"),

    /**
     * 已付款 (1)
     */
    PAID(1, "已付款"),

    /**
     * 已发货 (2)
     */
    SHIPPED(2, "已发货"),

    /**
     * 已完成 (3)
     */
    COMPLETED(3, "已完成"),

    /**
     * 已取消 (4)
     */
    CANCELLED(4, "已取消");

    private final int code;
    private final String description;

    /**
     * 构造函数
     * @param code 状态码
     * @param description 状态描述
     */
    OrderStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 获取状态码
     * @return 状态码
     */
    public int getCode() {
        return code;
    }

    /**
     * 获取状态描述
     * @return 状态描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 根据状态码获取枚举实例
     * @param code 状态码
     * @return 对应的枚举实例
     * @throws IllegalArgumentException 如果找不到对应的枚举值
     */
    public static OrderStatus fromCode(int code) {
        for (OrderStatus status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("无效的订单状态码: " + code);
    }

    /**
     * 检查当前状态是否允许取消订单
     * @return 是否允许取消
     */
    public boolean canBeCancelled() {
        return this == PENDING || this == PAID;
    }

    /**
     * 检查当前状态是否已完成（包括已完成和已取消）
     * @return 是否已完成
     */
    public boolean isFinalStatus() {
        return this == COMPLETED || this == CANCELLED;
    }
}