// src/pages/CartPage.jsx
import React from 'react';
import { Typography, Button, List, Card, InputNumber } from 'antd';
import { Link, useNavigate } from 'react-router-dom';

const { Title, Paragraph } = Typography;

function CartPage({ cartItems, onRemoveItem, onClearCart, onQuantityChange }) {
    const navigate = useNavigate();

    const handleCheckout = () => {
        // Здесь должна быть логика оформления заказа
        navigate('/order-complete');
    };

    const handleRemoveItem = (item) => {
        onRemoveItem(item.id, item.size);
    };

    const handleClearCart = () => {
        onClearCart();
    };

    const handleQuantityChange = (quantity, item) => {
        if (quantity >= 1 && quantity <= item.availableCount) {
            onQuantityChange(item.id, item.size, quantity);
        } else if (quantity < 1) {
            onQuantityChange(item.id, item.size, 1); // Минимальное значение 1
        } else {
            // Можно добавить визуальное уведомление о превышении доступного количества
            onQuantityChange(item.id, item.size, item.availableCount); // Максимальное значение
        }
    };

    if (!cartItems || cartItems.length === 0) {
        return (
            <div style={{ padding: 24, backgroundColor: '#fff', textAlign: 'center' }}>
                <Title level={2}>Корзина пока пустая</Title>
                <Paragraph>Добавьте товары и оформите заказ.</Paragraph>
                <Link to="/">
                    <Button type="primary">На главную</Button>
                </Link>
            </div>
        );
    }

    const totalPrice = cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);

    return (
        <div style={{ padding: 24, backgroundColor: '#fff' }}>
            <Title level={2}>Корзина ({cartItems.length} товара)</Title>
            {cartItems.length > 0 && (
                <div style={{ marginBottom: 16, textAlign: 'right' }}>
                    <Button danger onClick={handleClearCart}>Очистить корзину</Button>
                </div>
            )}
            <List
                itemLayout="horizontal"
                dataSource={cartItems}
                renderItem={item => (
                    <List.Item style={{ padding: 0 }}>
                        <Card style={{ width: '100%', margin: '10px 0' }}>
                            <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
                                <div style={{ display: 'flex', alignItems: 'center' }}>
                                    {item.image_url && (
                                        <img
                                            src={`http://localhost:8080${item.image_url}`}
                                            alt={item.name}
                                            style={{ width: 80, height: 80, marginRight: 16, objectFit: 'cover' }}
                                        />
                                    )
                                }
                                    <div style={{ flexGrow: 1 }}>
                                        <Typography.Text strong>{item.name}</Typography.Text>
                                        <Paragraph type="secondary">{item.size}</Paragraph>
                                        <Typography.Text>Цена: {item.price} $.</Typography.Text>
                                    </div>
                                </div>
                                <div style={{ display: 'flex', alignItems: 'center' }}>
                                    <Typography.Text style={{ marginRight: 8 }}>Кол-во:</Typography.Text>
                                    <InputNumber
                                        min={1}
                                        max={item.availableCount}
                                        defaultValue={item.quantity}
                                        style={{ width: 70, marginRight: 16 }}
                                        onChange={(quantity) => handleQuantityChange(quantity, item)}
                                    />
                                    <Typography.Text strong>{(item.price * item.quantity).toFixed(2)} $.</Typography.Text>
                                    <Button size="small" danger style={{ marginLeft: 16 }} onClick={() => handleRemoveItem(item)}>Удалить</Button>
                                </div>
                            </div>
                        </Card>
                    </List.Item>
                )}
                footer={
                    <div style={{ textAlign: 'right', marginTop: 24 }}>
                        <Typography.Title level={3}>Итого: {totalPrice.toFixed(2)} $.</Typography.Title>
                        <Button type="primary" size="large" onClick={handleCheckout}>Оформить заказ</Button>
                    </div>
                }
            />
        </div>
    );
}

export default CartPage;