import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { Typography, Image, Button, Spin, Radio, Select } from 'antd';
import axios from 'axios';

const { Title, Paragraph } = Typography;
const { Option } = Select;

const categoryMap = {
    "Shirt": "Футболка",
    "Pants": "Штаны",
    "Shoes": "Обувь",
    "Accessories": "Аксессуары",
    "Socks": "Носки",
    "Glasses": "Очки",
    "Scarf":"Шарф",
    "Shorts":"Шорты",
    "Sweatshirt":"Свитшот",
    "Jacket":"Куртка",
    "Slippers":"Тапки",
    "Hat":"Головной убор"
};

const availableSizesOrder = ['XSMALL', 'SMALL', 'MEDIUM', 'LARGE', 'XLARGE'];

function ClothDetailPage() {
    const { id } = useParams();
    const [product, setProduct] = useState(null);
    const [sizes, setSizes] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [sizesLoading, setSizesLoading] = useState(true);
    const [sizesError, setSizesError] = useState(null);
    const [selectedSize, setSelectedSize] = useState(null);

    useEffect(() => {
        const fetchProductDetails = async () => {
            setLoading(true);
            setError(null);
            try {
                const response = await axios.get(`http://localhost:8080/api/clothes/${id}`);
                setProduct(response.data);
            } catch (err) {
                console.error('Ошибка при загрузке деталей товара:', err);
                setError('Не удалось загрузить детали товара');
            } finally {
                setLoading(false);
            }
        };

        const fetchProductSizes = async () => {
            setSizesLoading(true);
            setSizesError(null);
            try {
                const response = await axios.get(`http://localhost:8080/api/clothes/${id}/sizes`);
                const fetchedSizes = response.data;
                const allSizes = availableSizesOrder.map(size => {
                    const found = fetchedSizes.find(s => s.size === size);
                    return { size: size, count: found ? found.count : 0 };
                });
                setSizes(allSizes);
            } catch (err) {
                console.error('Ошибка при загрузке размеров товара:', err);
                setSizesError('Не удалось загрузить размеры товара');
            } finally {
                setSizesLoading(false);
            }
        };

        fetchProductDetails();
        fetchProductSizes();
    }, [id]);

    if (loading || sizesLoading) {
        return <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '80vh' }}><Spin size="large" /></div>;
    }

    if (error) {
        return <div style={{ textAlign: 'center', marginTop: 50 }}>Ошибка: {error}</div>;
    }

    if (sizesError) {
        return <div style={{ textAlign: 'center', marginTop: 20, color: 'red' }}>Ошибка загрузки размеров: {sizesError}</div>;
    }

    if (!product) {
        return <div>Товар не найден</div>;
    }

    const handleSizeChange = (value) => {
        setSelectedSize(value);
        console.log('Выбранный размер:', value);
    };

    const scaleFactor = 1;

    return (
        <div style={{ padding: 24, backgroundColor: '#fff', display: 'flex', justifyContent: 'center', alignItems: 'flex-start', transform: `scale(${scaleFactor})`, transformOrigin: 'top center' }}>
            <div style={{ display: 'flex', gap: 32 }}>
                {product.image_url && (
                    <Image
                        width={400}
                        src={`http://localhost:8080${product.image_url}`}
                        alt={product.name}
                    />
                )}
                <div>
                    <Title level={2}>{product.name}</Title>
                    <Paragraph style={{ fontSize: '1.2em', fontWeight: 'bold' }}>Цена: {product.price}$</Paragraph>
                    <Paragraph>Тип: {categoryMap[product.category] || product.category}</Paragraph>
                    <Paragraph>Пол: {product.sex === 'MALE' ? 'Мужской' : 'Женский'}</Paragraph>
                    {product.description && <Paragraph>{product.description}</Paragraph>}

                    <div style={{ marginTop: 16 }}>
                        <Paragraph strong>Размер:</Paragraph>
                        <Select
                            placeholder="Выберите размер"
                            onChange={handleSizeChange}
                            style={{ width: 120, color: '#000' }}
                        >
                            {sizes
                                .sort((a, b) => availableSizesOrder.indexOf(a.size) - availableSizesOrder.indexOf(b.size))
                                .map(sizeInfo => {
                                    const shortSize = {
                                        'XSMALL': 'XS',
                                        'SMALL': 'S',
                                        'MEDIUM': 'M',
                                        'LARGE': 'L',
                                        'XLARGE': 'XL'
                                    }[sizeInfo.size] || sizeInfo.size;
                                    return (
                                        <Option
                                            key={sizeInfo.size}
                                            value={sizeInfo.size}
                                            disabled={sizeInfo.count === 0}
                                        >
                                            {shortSize} - {sizeInfo.count}шт
                                        </Option>
                                    );
                                })}
                        </Select>
                    </div>

                    <Button type="primary" size="large" style={{ marginTop: 24 }} disabled={!selectedSize}>
                        Добавить в корзину
                    </Button>
                </div>
            </div>
        </div>
    );
}

export default ClothDetailPage;