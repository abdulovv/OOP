// src/pages/StatisticsPage.jsx
import React, { useState, useEffect } from 'react';
import { Typography, Row, Col, Card, Select, Spin, List, Avatar } from 'antd';
import axios from 'axios';

const { Title } = Typography;
const { Option } = Select;

function StatisticsPage() {
    const [selectedClothId, setSelectedClothId] = useState(null);
    const [salesData, setSalesData] = useState(null);
    const [topSelling, setTopSelling] = useState([]);
    const [leastSelling, setLeastSelling] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [clothOptions, setClothOptions] = useState([]);

    useEffect(() => {
        const fetchClothOptions = async () => {
            setLoading(true);
            try {
                const response = await axios.get('http://localhost:8080/api/clothes/');
                setClothOptions(response.data.map(cloth => ({
                    value: cloth.id,
                    label: cloth.name,
                    image: cloth.image_url ? `http://localhost:8080${cloth.image_url}` : null,
                })));
            } catch (error) {
                console.error('Ошибка при загрузке списка товаров:', error);
                setError('Ошибка при загрузке списка товаров');
            } finally {
                setLoading(false);
            }
        };

        const fetchTopAndLeastSelling = async () => {
            setLoading(true);
            try {
                const [topResponse, leastResponse] = await Promise.all([
                    axios.get('http://localhost:8080/api/statistics/top-selling-overall'),
                    axios.get('http://localhost:8080/api/statistics/least-selling-overall')
                ]);
                setTopSelling(topResponse.data);
                setLeastSelling(leastResponse.data);
            } catch (error) {
                console.error('Ошибка при загрузке топов:', error);
                setError('Ошибка при загрузке топов');
            } finally {
                setLoading(false);
            }
        };

        fetchClothOptions();
        fetchTopAndLeastSelling();
    }, []);

    useEffect(() => {
        const fetchSalesData = async () => {
            if (selectedClothId) {
                setLoading(true);
                try {
                    const response = await axios.get(`http://localhost:8080/api/statistics/sales-by-cloth-size/${selectedClothId}`);
                    setSalesData(response.data);
                } catch (error) {
                    console.error('Ошибка при загрузке данных о продажах:', error);
                    setError('Ошибка при загрузке данных о продажах');
                } finally {
                    setLoading(false);
                }
            }
        };

        fetchSalesData();
    }, [selectedClothId]);

    const handleClothChange = (value) => {
        setSelectedClothId(value);
        setSalesData(null); // Сбрасываем предыдущие данные
    };

    return (
        <div style={{ padding: 24, backgroundColor: '#f0f2f5' }}>
            <Title level={1} style={{ textAlign: 'center', marginBottom: 24 }}>Статистика магазина</Title>

            {loading && <div style={{ textAlign: 'center' }}><Spin size="large" /></div>}
            {error && <div style={{ color: 'red', textAlign: 'center' }}>{error}</div>}

            <Row gutter={16}>
                <Col span={24}>
                    <Card title="Статистика продаж по товару" bordered={false} variant="outlined" style={{ marginBottom: 24 }}>
                        <Select
                            placeholder="Выберите товар"
                            style={{ width: '100%', marginBottom: 16 }}
                            onChange={handleClothChange}
                            loading={loading && clothOptions.length === 0}
                        >
                            {clothOptions.map(option => (
                                <Option key={option.value} value={option.value} label={option.label}>
                                    {option.image && <Avatar src={option.image} size="small" style={{ marginRight: 8 }} />}
                                    {option.label}
                                </Option>
                            ))}
                        </Select>

                        {salesData && Object.keys(salesData).length > 0 ? (
                            <div>
                                <Typography.Title level={4} style={{ marginTop: 16 }}>Продажи по размерам:</Typography.Title>
                                <List
                                    dataSource={Object.entries(salesData)}
                                    renderItem={([size, count]) => (
                                        <List.Item>
                                            {`Размер ${size}: продано ${count} шт.`}
                                        </List.Item>
                                    )}
                                />
                            </div>
                        ) : selectedClothId && !loading ? (
                            <p style={{ textAlign: 'center', marginTop: 16 }}>Нет данных о продажах для выбранного товара.</p>
                        ) : !selectedClothId && !loading ? (
                            <p style={{ textAlign: 'center', marginTop: 16 }}>Выберите товар, чтобы увидеть статистику продаж по размерам.</p>
                        ) : null}
                    </Card>
                </Col>
            </Row>

            <Row gutter={16}>
                <Col span={12}>
                    <Card title="Топ 5 продаваемых товаров" bordered={false} variant="outlined" style={{ marginBottom: 24 }}>
                        <List
                            itemLayout="horizontal"
                            dataSource={topSelling}
                            loading={loading && topSelling.length === 0}
                            renderItem={item => (
                                <List.Item>
                                    <List.Item.Meta
                                        avatar={<Avatar src={`http://localhost:8080${item.imageUrl}`} />}
                                        title={item.name}
                                        description={`Продано: ${item.totalSales}`}
                                    />
                                </List.Item>
                            )}
                        />
                        {topSelling.length === 0 && !loading && <p style={{ textAlign: 'center', marginTop: 16 }}>Нет данных о продажах.</p>}
                    </Card>
                </Col>
                <Col span={12}>
                    <Card title="Топ 5 непродаваемых товаров" bordered={false} variant="outlined" style={{ marginBottom: 24 }}>
                        <List
                            itemLayout="horizontal"
                            dataSource={leastSelling}
                            loading={loading && leastSelling.length === 0}
                            renderItem={item => (
                                <List.Item>
                                    <List.Item.Meta
                                        avatar={<Avatar src={`http://localhost:8080${item.imageUrl}`} />}
                                        title={item.name}
                                    />
                                </List.Item>
                            )}
                        />
                        {leastSelling.length === 0 && !loading && <p style={{ textAlign: 'center', marginTop: 16 }}>Все товары продаются.</p>}
                    </Card>
                </Col>
            </Row>
        </div>
    );
}

export default StatisticsPage;