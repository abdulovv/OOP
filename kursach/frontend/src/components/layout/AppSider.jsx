import React, { useState } from 'react';
import { Layout, Input, Select, Checkbox, Button } from 'antd';
import { SearchOutlined } from '@ant-design/icons';

export default function AppSider() {
    const [searchQuery, setSearchQuery] = useState('');
    const [selectedSex, setSelectedSex] = useState(''); // Для выбора пола
    const [selectedCategory, setSelectedCategory] = useState('');
    const [selectedSizes, setSelectedSizes] = useState([]); // Для множественного выбора размеров
    const [priceFrom, setPriceFrom] = useState(''); // Цена от
    const [priceTo, setPriceTo] = useState(''); // Цена до

    const handleSearch = (value) => {
        console.log('Поиск:', value);
        // Здесь можно вызвать функцию поиска, фильтрации, отправить API-запрос и т.д.
    };

    const handleSizeChange = (size, checked) => {
        if (checked) {
            setSelectedSizes([...selectedSizes, size]);
        } else {
            setSelectedSizes(selectedSizes.filter((s) => s !== size));
        }
    };

    const resetFilters = () => {
        setSearchQuery('');
        setSelectedSex('');
        setSelectedSizes([]);
        setPriceFrom('')
        setPriceTo('')
        setSelectedCategory('')
    };

    // Функция для ограничения ввода только цифр и точки
    const validateNumberInput = (e) => {
        const input = e.target.value;
        const regex = /^\d*\.?\d*$/; // Разрешаем только цифры и одну точку
        if (!regex.test(input)) {
            e.target.value = input.replace(/[^0-9.]/g, '');
        }
    };

    const validateSearchInput = (e) => {
        let input = e.target.value;

        // Регулярное выражение: разрешаем латиницу, кириллицу, цифры и пробелы
        const regex = /^[A-Za-zА-Яа-я0-9 ]*$/;

        if (!regex.test(input)) {
            // Если есть запрещённые символы — удаляем их
            e.target.value = input.replace(/[^A-Za-zА-Яа-я0-9 ]/g, '');
        }
    };

    return (
        <Layout.Sider
            style={{
                textAlign: 'center',
                height: '100vh',
                color: '#fff',
                backgroundColor: '#171717',
                borderRight: '1.2px solid #000',
                display: 'flex',
                flexDirection: 'column',
                justifyContent: 'start',
                padding: '40px 20px',
                boxSizing: 'border-box',
                overflowY: 'auto',
            }}
            width={'20%'}
        >
            {/* Блок с поиском */}
            <div style={{ width: '100%', maxWidth: '500px', marginBottom: '30px' }}>
                <Input
                    placeholder="Поиск..."
                    prefix={<SearchOutlined />}
                    value={searchQuery}
                    onChange={(e) => {
                        setSearchQuery(e.target.value);
                        handleSearch(e.target.value);
                    }}
                    onInput={validateSearchInput}
                    style={{
                        width: '100%',
                        backgroundColor: '#196807',
                        border: '2px solid #3e3e3e',
                        padding: '10px 14px',
                        color: '#fff',
                        borderRadius: '6px',
                        fontSize: '16px',
                        boxSizing: 'border-box',
                        height: '44px',
                    }}
                />

                {/* Разделительная линия с текстом "Filter" посередине */}
                <div
                    style={{
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'space-between',
                        height: '20px',
                        margin: '20px 0',
                    }}
                >
                    <div style={{ flex: 1, height: '1px', backgroundColor: '#444' }} />
                    <span
                        style={{
                            color: '#aaa',
                            fontSize: '14px',
                            fontWeight: 500,
                            whiteSpace: 'nowrap',
                            padding: '0 10px',
                            textAlign: 'center',
                        }}
                    >
            Filter
          </span>
                    <div style={{ flex: 1, height: '1px', backgroundColor: '#444' }} />
                </div>
            </div>

            {/* Блок с фильтром "Пол" */}
            <div
                style={{
                    width: '100%',
                    maxWidth: '500px',
                    marginBottom: '30px',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'space-between',
                    gap: '10px',
                    flexWrap: 'nowrap',
                }}
            >
        <span
            style={{
                color: '#ccc',
                fontSize: '14px',
                fontWeight: 500,
                whiteSpace: 'nowrap',
                textAlign: 'left',
                flexShrink: 0,
            }}
        >
          Пол:
        </span>
                <Select
                    placeholder="Не выбрано"
                    value={selectedSex}
                    onChange={setSelectedSex}
                    style={{
                        flex: 1,
                        backgroundColor: '#196807',
                        border: '2px solid #3e3e3e',
                        borderRadius: '6px',
                        color: '#fff',
                        height: '44px',
                        fontSize: '16px',
                    }}
                    dropdownRender={(menu) => (
                        <div style={{ backgroundColor: '#196807', color: '#fff' }}>{menu}</div>
                    )}
                >
                    <Select.Option value="">Не выбрано</Select.Option>
                    <Select.Option value="male">Мужской</Select.Option>
                    <Select.Option value="female">Женский</Select.Option>
                </Select>
            </div>

            {/* Блок с фильтром "Категория" */}
            <div
                style={{
                    width: '100%',
                    maxWidth: '500px',
                    marginBottom: '30px',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'space-between',
                    gap: '10px',
                    flexWrap: 'nowrap',
                }}
            >
        <span
            style={{
                color: '#ccc',
                fontSize: '14px',
                fontWeight: 500,
                whiteSpace: 'nowrap',
                textAlign: 'left',
                flexShrink: 0,
            }}
        >
          Категория:
        </span>
                <Select
                    placeholder="Выберите категорию"
                    value={selectedCategory}
                    onChange={setSelectedCategory}
                    style={{
                        flex: 1,
                        backgroundColor: '#196807',
                        border: '2px solid #3e3e3e',
                        borderRadius: '6px',
                        color: '#fff',
                        height: '44px',
                        fontSize: '16px',
                    }}
                    dropdownRender={(menu) => (
                        <div style={{ backgroundColor: '#196807', color: '#fff' }}>{menu}</div>
                    )}
                >
                    <Select.Option value="">Не выбрано</Select.Option>
                    <Select.Option value="FootWear">Обувь</Select.Option>
                    <Select.Option value="TopWear">Верхняя одежда</Select.Option>
                    <Select.Option value="LegWear">Шорты / Штаны</Select.Option>
                    <Select.Option value="HeadWear">Головные уборы</Select.Option>
                </Select>
            </div>

            {/* Блок с фильтром "Размер" (чекбоксы) */}
            <div
                style={{
                    width: '100%',
                    maxWidth: '500px',
                    marginBottom: '30px',
                }}
            >
        <span
            style={{
                color: '#ccc',
                fontSize: '14px',
                fontWeight: 500,
                whiteSpace: 'nowrap',
                textAlign: 'left',
                display: 'block',
                marginBottom: '10px',
            }}
        >
          Размер:
        </span>

                <div style={{ display: 'flex', flexDirection: 'column', gap: '5px' }}>
                    <Checkbox
                        checked={selectedSizes.includes('XS')}
                        onChange={(e) => handleSizeChange('XS', e.target.checked)}
                        style={{ color: '#3dcf1a' }}
                    >
                        XS
                    </Checkbox>
                    <Checkbox
                        checked={selectedSizes.includes('S')}
                        onChange={(e) => handleSizeChange('S', e.target.checked)}
                        style={{ color: '#3dcf1a' }}
                    >
                        S
                    </Checkbox>
                    <Checkbox
                        checked={selectedSizes.includes('M')}
                        onChange={(e) => handleSizeChange('M', e.target.checked)}
                        style={{ color: '#3dcf1a' }}
                    >
                        M
                    </Checkbox>
                    <Checkbox
                        checked={selectedSizes.includes('L')}
                        onChange={(e) => handleSizeChange('L', e.target.checked)}
                        style={{ color: '#3dcf1a' }}
                    >
                        L
                    </Checkbox>
                    <Checkbox
                        checked={selectedSizes.includes('XL')}
                        onChange={(e) => handleSizeChange('XL', e.target.checked)}
                        style={{ color: '#3dcf1a' }}
                    >
                        XL
                    </Checkbox>
                </div>
            </div>

            {/* Блок с ценой */}
            <div
                style={{
                    width: '100%',
                    maxWidth: '500px',
                    marginBottom: '30px',
                    display: 'flex',
                    alignItems: 'center',
                    gap: '10px',
                }}
            >
                <span style={{ color: '#ccc', fontSize: '14px', fontWeight: 500 }}>Цена от</span>
                <Input
                    placeholder="От"
                    style={{
                        flex: 1,
                        backgroundColor: '#196807',
                        border: '2px solid #3e3e3e',
                        padding: '10px 14px',
                        color: '#fff',
                        borderRadius: '6px',
                        fontSize: '16px',
                        boxSizing: 'border-box',
                        height: '44px',
                    }}
                    value={priceFrom}
                    onChange={(e) => setPriceFrom(e.target.value)}
                    onInput={(e) => validateNumberInput(e, 'from')} // Проверка диапазона
                />
                <span style={{ color: '#ccc', fontSize: '14px', fontWeight: 500 }}>до</span>
                <Input
                    placeholder="До"
                    style={{
                        flex: 1,
                        backgroundColor: '#196807',
                        border: '2px solid #3e3e3e',
                        padding: '10px 14px',
                        color: '#fff',
                        borderRadius: '6px',
                        fontSize: '16px',
                        boxSizing: 'border-box',
                        height: '44px',
                    }}
                    value={priceTo}
                    onChange={(e) => setPriceTo(e.target.value)}
                    onInput={(e) => validateNumberInput(e, 'to')} // Проверка диапазона
                />
            </div>

            {/* Полосочка Reset */}

            <div
                style={{
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'space-between',
                    height: '20px',
                    margin: '20px 0',
                }}
            >
                <div style={{ flex: 1, height: '1px', backgroundColor: '#444' }} />
                <span
                    style={{
                        color: '#aaa',
                        fontSize: '14px',
                        fontWeight: 500,
                        whiteSpace: 'nowrap',
                        padding: '0 10px',
                        textAlign: 'center',
                    }}
                >
            Reset
          </span>
                <div style={{ flex: 1, height: '1px', backgroundColor: '#444' }} />
            </div>

            {/* Кнопка Сбросить */}
            <Button
                type="primary"
                onClick={resetFilters}
                style={{
                    width: '100%',
                    maxWidth: '500px',
                    backgroundColor: '#196807',
                    border: 'none',
                    color: '#fff',
                    borderRadius: '6px',
                    fontSize: '16px',
                    height: '44px',
                    marginTop: '20px',
                }}
            >
                Сбросить
            </Button>
        </Layout.Sider>
    );
}