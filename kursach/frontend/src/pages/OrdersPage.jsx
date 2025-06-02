// src/pages/OrdersPage.jsx
import React, { useState, useEffect } from 'react';
import { Typography, Table, Spin } from 'antd'; // Import Avatar
import axios from 'axios';

const { Title } = Typography;

function OrdersPage() {
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [expandedOrderItems, setExpandedOrderItems] = useState({});
    const [loadingOrderItems, setLoadingOrderItems] = useState({});

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

    const fetchOrderItems = async (orderId) => {
        setLoadingOrderItems(prevState => ({ ...prevState, [orderId]: true }));
        try {
            const response = await axios.get(`http://localhost:8080/api/orders/${orderId}/items`);
            setExpandedOrderItems(prevState => ({ ...prevState, [orderId]: response.data }));
        } catch (error) {
            console.error(`Ошибка при загрузке деталей заказа ${orderId}:`, error);
            setError('Ошибка при загрузке деталей заказа');
        } finally {
            setLoadingOrderItems(prevState => ({ ...prevState, [orderId]: false }));
        }
    };

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
        const orderItems = expandedOrderItems[record.id];
        const loadingItems = loadingOrderItems[record.id];

        if (loadingItems) {
            return <div style={{ textAlign: 'center', padding: 24 }}><Spin size="small" /></div>;
        }

        return (
            <div style={{ margin: 0, padding: '16px 0' }}>
                <Typography.Text strong>Содержимое заказа:</Typography.Text>
                {orderItems && orderItems.length > 0 ? (
                    orderItems.map((item, index) => (
                        <div key={index} style={{ display: 'flex', alignItems: 'center', marginBottom: 8 }}>

                            <div style={{ marginLeft: 8 }}>
                                <Typography.Text strong>{item.clothName}</Typography.Text>
                                <br />
                                <Typography.Text type="secondary">
                                    {item.size}, {item.count} шт.
                                </Typography.Text>
                            </div>
                        </div>
                    ))
                ) : (
                    <div>Нет товаров в заказе.</div>
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
                    onExpand: (expanded, record) => {
                        if (expanded) {
                            fetchOrderItems(record.id);
                        }
                    },
                    rowExpandable: record => true,
                }}
                pagination={false}
            />
        </div>
    );
}

export default OrdersPage;