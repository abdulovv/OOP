import React, { useState, useEffect } from 'react';
import { Typography } from 'antd';
import ProductCard from '../components/ProductCard';
import axios from 'axios';

const { Title } = Typography;

function CatalogPage() {
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        axios.get('http://localhost:8080/api/clothes')
            .then(response => {
                setProducts(response.data);
                setLoading(false);
            })
            .catch(error => {
                setError(error);
                setLoading(false);
                console.error('Ошибка при загрузке товаров:', error);
            });
    }, []);

    if (loading) {
        return <div>Загрузка товаров...</div>;
    }

    if (error) {
        return <div>Ошибка при загрузке товаров!</div>;
    }

    const rows = [];
    for (let i = 0; i < products.length; i += 3) {
        rows.push(products.slice(i, i + 3));
    }

    return (
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
            <Title level={2} style={{ marginBottom: 24, marginTop: 0 }}>Каталог товаров</Title>
            <div style={{ width: '100%', maxWidth: 1200, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                {rows.map((row, index) => (
                    <div key={index} style={{ display: 'flex', justifyContent: 'center', marginBottom: 16 }}>
                        {row.map(product => (
                            <div key={product.id} style={{ margin: '0 16px' }}>
                                <ProductCard product={product} />
                            </div>
                        ))}
                    </div>
                ))}
            </div>
        </div>
    );
}

export default CatalogPage;