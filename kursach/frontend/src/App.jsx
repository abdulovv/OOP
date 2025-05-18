import { Layout } from 'antd';

import AppContent from "./components/layout/AppContent";
import AppFooter from "./components/layout/AppFooter";
import AppHeader from "./components/layout/AppHeader";
import AppSider from "./components/layout/AppSider";

const layoutStyle = {
  borderRadius: 8,
  overflow: 'hidden',
  height: 'calc(100vh)',
  width: 'calc(100%)',
  maxWidth: 'calc(100%)',
};

const App = () => (

      <Layout style={layoutStyle}>
        <AppSider />
        <Layout>
          <AppHeader />
          <AppContent />
          <AppFooter />
        </Layout>
      </Layout>

);
export default App;