import React, { useState, useEffect } from 'react';
import { Form, Input, InputNumber, Select, Button, Table, message } from 'antd';
import axios from 'axios';

const { Option } = Select;

const AdminPanelPage = () => {
    const [form] = Form.useForm();
    const [clothes, setClothes] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        fetchClothes();
    }, []);

    const fetchClothes = async () => {
        setLoading(true);
        try {
            const response = await axios.get('/api/clothes');
            setClothes(response.data);
        } catch (error) {
            console.error('Error fetching clothes:', error);
            message.error('Failed to fetch clothes');
        } finally {
            setLoading(false);
        }
    };

    const onFinish = async (values) => {
        try {
            const sizes = Object.keys(values).filter(key => key.startsWith('size_')).map(key => {
                const size = key.split('_')[1];
                return { size, count: values[key] };
            });

            const clothData = {
                ...values,
                sizes,
            };

            await axios.post('/api/clothes', clothData);
            message.success('Cloth created successfully');
            form.resetFields();
            fetchClothes();
        } catch (error) {
            console.error('Error creating cloth:', error);
            message.error('Failed to create cloth');
        }
    };

    const handleSizeCountChange = async (clothId, size, count) => {
        try {
            await axios.put(`/api/clothes/${clothId}/sizes`, [{ size, count }]);
            message.success(`Stock for ${size} updated`);
            fetchClothes();
        } catch (error) {
            console.error('Error updating stock:', error);
            message.error('Failed to update stock');
        }
    };

    const handleDeleteCloth = async (clothId) => {
        try {
            await axios.delete(`/api/clothes/${clothId}`);
            message.success('Cloth deleted successfully');
            fetchClothes();
        } catch (error) {
            console.error('Error deleting cloth:', error);
            message.error('Failed to delete cloth');
        }
    };

    const columns = [
        {
            title: 'Name',
            dataIndex: 'name',
            key: 'name',
        },
        {
            title: 'Price',
            dataIndex: 'price',
            key: 'price',
        },
        {
            title: 'Category',
            dataIndex: 'category',
            key: 'category',
            render: (category) => category,
        },
        {
            title: 'Sex',
            dataIndex: 'sex',
            key: 'sex',
            render: (sex) => sex,
        },
        {
            title: 'Sizes',
            key: 'sizes',
            render: (record) => (
                <div>
                    {record.clothSizes && record.clothSizes.map(size => (
                        <div key={size.size}>
                            {size.size}:
                            <InputNumber
                                min={0}
                                value={size.count}
                                onChange={(value) => handleSizeCountChange(record.id, size.size, value)}
                            />
                        </div>
                    ))}
                </div>
            ),
        },
        {
            title: 'Action',
            key: 'action',
            render: (record) => (
                <Button danger onClick={() => handleDeleteCloth(record.id)}>
                    Delete
                </Button>
            ),
        },
    ];

    const clothData = clothes.map(cloth => ({
        ...cloth,
        key: cloth.id,
        clothSizes: cloth.clothSizes,
    }));

    return (
        <div>
            <h1>Admin Panel</h1>

            {/* Убрали Collapse и Panel */}
            <Form form={form} layout="vertical" onFinish={onFinish}>
                <Form.Item label="Name" name="name" rules={[{ required: true }]}>
                    <Input />
                </Form.Item>
                <Form.Item label="Price" name="price" rules={[{ required: true, type: 'number' }]}>
                    <InputNumber />
                </Form.Item>
                <Form.Item label="Category" name="category" rules={[{ required: true }]}>
                    <Select>
                        <Option value="Hat">Hat</Option>
                        <Option value="Slippers">Slippers</Option>
                        <Option value="Shorts">Shorts</Option>
                        <Option value="Shoes">Shoes</Option>
                        <Option value="Shirt">Shirt</Option>
                        <Option value="Socks">Socks</Option>
                        <Option value="Jacket">Jacket</Option>
                        <Option value="Pants">Pants</Option>
                        <Option value="Sweatshirt">Sweatshirt</Option>
                        <Option value="Glasses">Glasses</Option>
                        <Option value="Scarf">Scarf</Option>
                    </Select>
                </Form.Item>
                <Form.Item label="Sex" name="sex" rules={[{ required: true }]}>
                    <Select>
                        <Option value="MALE">Male</Option>
                        <Option value="FEMALE">Female</Option>
                    </Select>
                </Form.Item>
                <Form.Item label="Image URL" name="image_url" rules={[{ required: true, type: 'url' }]}>
                    <Input />
                </Form.Item>
                <Form.Item label="Size XS Count" name="size_XS" rules={[{ required: true, type: 'number' }]}>
                    <InputNumber min={0} />
                </Form.Item>
                <Form.Item label="Size S Count" name="size_S" rules={[{ required: true, type: 'number' }]}>
                    <InputNumber min={0} />
                </Form.Item>
                <Form.Item label="Size M Count" name="size_M" rules={[{ required: true, type: 'number' }]}>
                    <InputNumber min={0} />
                </Form.Item>
                <Form.Item label="Size L Count" name="size_L" rules={[{ required: true, type: 'number' }]}>
                    <InputNumber min={0} />
                </Form.Item>
                <Form.Item label="Size XL Count" name="size_XL" rules={[{ required: true, type: 'number' }]}>
                    <InputNumber min={0} />
                </Form.Item>
                <Form.Item>
                    <Button type="primary" htmlType="submit">
                        Create Cloth
                    </Button>
                </Form.Item>
            </Form>

            <h2>Cloth List</h2>
            <Table columns={columns} dataSource={clothData} loading={loading} />
        </div>
    );
};

export default AdminPanelPage;