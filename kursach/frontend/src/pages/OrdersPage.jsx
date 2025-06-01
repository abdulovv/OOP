// src/pages/OrdersPage.jsx
import React, { useState, useEffect } from 'react';
import { Typography, Table, Spin } from 'antd';
import axios from 'axios';

const { Title } = Typography;

function OrdersPage() {
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchOrders = async () => {
            setLoading(true);
            try {
                const response = await axios.get('http://localhost:8080/api/orders');
                setOrders(response.data);
            } catch (error) {
                console.error('Ошибка при загрузке заказов:', error);
                setError('Ошибка при загрузке заказов');
            } finally {
                setLoading(false);
            }
        };

        fetchOrders();
    }, []);

    const columns = [
        {
            title: 'Номер заказа',
            dataIndex: 'id',
            key: 'id',
        },
        {
            title: 'Имя клиента',
            dataIndex: 'fullName',
            key: 'fullName',
        },
        {
            title: 'Контакт клиента',
            dataIndex: 'contactInfo',
            key: 'contactInfo',
        },
        {
            title: 'Время заказа',
            dataIndex: 'orderDate',
            key: 'orderDate',
            render: (text) => text ? new Date(text).toLocaleString() : 'Нет данных',
        },
    ];

    const expandedRowRender = (record) => {
        return (
            <div style={{ margin: 0, padding: '16px 0' }}>
                <Typography.Text strong>Содержимое заказа:</Typography.Text>
                <ul>
                    {record.orderItems && record.orderItems.map(item => (
                        <li key={item.id}>
                            {item.cloth.name} ({item.size}) x {item.quantity} - ${item.price}
                        </li>
                    ))}
                    {!record.orderItems || record.orderItems.length === 0 ? (
                        <li>Нет товаров в заказе.</li>
                    ) : null}
                </ul>
                {record.totalAmount !== undefined && (
                    <>
                        <Typography.Text strong>Сумма заказа:</Typography.Text>
                        <Typography.Text> ${record.totalAmount}</Typography.Text>
                    </>
                )}
            </div>
        );
    };

    if (loading) {
        return <div style={{ textAlign: 'center', padding: 24 }}><Spin size="large" /></div>;
    }

    if (error) {
        return <div style={{ color: 'red', textAlign: 'center', padding: 24 }}>{error}</div>;
    }

    return (
        <div style={{ padding: 24 }}>
            <Title level={1} style={{ textAlign: 'center', marginBottom: 24 }}>Все заказы</Title>
            <Table
                dataSource={orders}
                columns={columns}
                rowKey="id"
                expandable={{
                    expandedRowRender,
                    rowExpandable: record => true, // Временно разрешаем разворачивать все строки
                }}
                pagination={false}
            />
        </div>
    );
}

export default OrdersPage;