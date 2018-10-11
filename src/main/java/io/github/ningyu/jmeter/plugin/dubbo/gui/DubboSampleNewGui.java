package io.github.ningyu.jmeter.plugin.dubbo.gui;

import io.github.ningyu.jmeter.plugin.dubbo.sample.DubboSample;
import io.github.ningyu.jmeter.plugin.dubbo.sample.DubboSampleNew;
import org.apache.jmeter.gui.util.HorizontalPanel;
import org.apache.jmeter.gui.util.JSyntaxTextArea;
import org.apache.jmeter.gui.util.JTextScrollPane;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import javax.swing.*;
import java.awt.*;


/**
 * @author 林杰炜 linjiewei
 * @Title TODO 类描述
 * @Description TODO 详细描述
 * @date 2018/7/23 23:57
 */
public class DubboSampleNewGui extends AbstractSamplerGui {

    private static final Logger log = LoggingManager.getLoggerForClass();
    private static final long serialVersionUID = 8516720977108089770L;

    private JComboBox<String> registryProtocolText;
    private JComboBox<String> rpcProtocolText;
    private JTextField addressText;
    private JTextField timeoutText;
    private JTextField versionText;
    private JTextField retriesText;
    private JTextField clusterText;
    private JTextField interfaceText;
    private JTextField methodText;
    private JTextField groupText;
    private JTextField connectionsText;
    private JComboBox<String> loadbalanceText;
    private JComboBox<String> asyncText;
    private JSyntaxTextArea argsBodyContent;
    private int textColumns = 2;

    public DubboSampleNewGui() {
        super();
        init();
    }

    private void init(){
        //所有设置panel，垂直布局
        JPanel settingPanel = new VerticalPanel(5, 0);
        settingPanel.setBorder(makeBorder());
        Container container = makeTitlePanel();
        settingPanel.add(container);

        //Registry Settings
        JPanel registrySettings = new VerticalPanel();
        registrySettings.setBorder(BorderFactory.createTitledBorder("Registry Settings"));
        //Protocol
        JPanel ph = new HorizontalPanel();
        JLabel protocolLable = new JLabel("Protocol:", SwingConstants.RIGHT);
        registryProtocolText = new JComboBox<String>(new String[]{"none", "zookeeper", "multicast", "redis", "simple"});
        registryProtocolText.setToolTipText("\"none\" is direct connection");
        protocolLable.setLabelFor(registryProtocolText);
        ph.add(protocolLable);
        ph.add(registryProtocolText);
        registrySettings.add(ph);
        //Address
        JPanel ah = new HorizontalPanel();
        JLabel addressLable = new JLabel("Address:", SwingConstants.RIGHT);
        addressText = new JTextField(textColumns);
        addressLable.setLabelFor(addressText);
        JLabel addressHelpLable = new JLabel();
        addressHelpLable.setIcon(new ImageIcon(getClass().getResource("/images/help.png")));
        addressHelpLable.setToolTipText("Use the registry to allow multiple addresses, Use direct connection to allow only one address! Multiple address format: ip1:port1,ip2:port2 . Direct address format: ip:port . ");
        ah.add(addressLable);
        ah.add(addressText);
        ah.add(addressHelpLable);
        registrySettings.add(ah);

        //RPC Protocol Settings
        JPanel protocolSettings = new VerticalPanel();
        protocolSettings.setBorder(BorderFactory.createTitledBorder("RPC Protocol Settings"));
        //RPC Protocol
        JPanel rpcPh = new HorizontalPanel();
        JLabel rpcProtocolLable = new JLabel("Protocol:", SwingConstants.RIGHT);
        rpcProtocolText = new JComboBox<String>(new String[]{"dubbo://", "rmi://", "hessian://", "webservice://", "memcached://", "redis://"});
        rpcProtocolLable.setLabelFor(rpcProtocolText);
        rpcPh.add(rpcProtocolLable);
        rpcPh.add(rpcProtocolText);
        protocolSettings.add(rpcPh);

        //Consumer Settings
        JPanel consumerSettings = new VerticalPanel();
        consumerSettings.setBorder(BorderFactory.createTitledBorder("Consumer Settings"));
        JPanel h = new HorizontalPanel();
        //Timeout
        JLabel timeoutLable = new JLabel(" Timeout:", SwingConstants.RIGHT);
        timeoutText = new JTextField(textColumns);
        timeoutText.setText(DubboSample.DEFAULT_TIMEOUT);
        timeoutLable.setLabelFor(timeoutText);
        h.add(timeoutLable);
        h.add(timeoutText);
        //Version
        JLabel versionLable = new JLabel("Version:", SwingConstants.RIGHT);
        versionText = new JTextField(textColumns);
        versionText.setText(DubboSample.DEFAULT_VERSION);
        versionLable.setLabelFor(versionText);
        h.add(versionLable);
        h.add(versionText);
        //Retries
        JLabel retriesLable = new JLabel("Retries:", SwingConstants.RIGHT);
        retriesText = new JTextField(textColumns);
        retriesText.setText(DubboSample.DEFAULT_RETRIES);
        retriesLable.setLabelFor(retriesText);
        h.add(retriesLable);
        h.add(retriesText);
        //Cluster
        JLabel clusterLable = new JLabel("Cluster:", SwingConstants.RIGHT);
        clusterText = new JTextField(textColumns);
        clusterText.setText(DubboSample.DEFAULT_CLUSTER);
        clusterLable.setLabelFor(clusterText);
        h.add(clusterLable);
        h.add(clusterText);
        //Group
        JLabel groupLable = new JLabel("Group:", SwingConstants.RIGHT);
        groupText = new JTextField(textColumns);
        groupText.setText(DubboSample.DEFAULT_GROUP);
        groupLable.setLabelFor(groupText);
        h.add(groupLable);
        h.add(groupText);
        //Connections
        JLabel connectionsLable = new JLabel("Connections:", SwingConstants.RIGHT);
        connectionsText = new JTextField(textColumns);
        connectionsText.setText(DubboSample.DEFAULT_CONNECTIONS);
        connectionsLable.setLabelFor(connectionsText);
        h.add(connectionsLable);
        h.add(connectionsText);
        consumerSettings.add(h);

        JPanel hp1 = new HorizontalPanel();
        //Async
        JLabel asyncLable = new JLabel("     Async:", SwingConstants.RIGHT);
        asyncText = new JComboBox<String>(new String[]{"sync", "async"});
        asyncLable.setLabelFor(asyncText);
        hp1.add(asyncLable);
        hp1.add(asyncText);
        //Loadbalance
        JLabel loadbalanceLable = new JLabel("Loadbalance:", SwingConstants.RIGHT);
        loadbalanceText = new JComboBox<String>(new String[]{"random", "roundrobin", "leastactive", "consistenthash"});
        loadbalanceLable.setLabelFor(loadbalanceText);
        hp1.add(loadbalanceLable);
        hp1.add(loadbalanceText);
        consumerSettings.add(hp1);

        //Interface Settings
        JPanel interfaceSettings = new VerticalPanel();
        interfaceSettings.setBorder(BorderFactory.createTitledBorder("Interface Settings"));
        //Interface
        JPanel ih = new HorizontalPanel();
        JLabel interfaceLable = new JLabel("Interface:", SwingConstants.RIGHT);
        interfaceText = new JTextField(textColumns);
        interfaceLable.setLabelFor(interfaceText);
        ih.add(interfaceLable);
        ih.add(interfaceText);
        interfaceSettings.add(ih);
        //Method
        JPanel mh = new HorizontalPanel();
        JLabel methodLable = new JLabel("   Method:", SwingConstants.RIGHT);
        methodText = new JTextField(textColumns);
        methodLable.setLabelFor(methodText);
        mh.add(methodLable);
        mh.add(methodText);
        interfaceSettings.add(mh);

        argsBodyContent = JSyntaxTextArea.getInstance(30, 50);
        JPanel argsPanel = new HorizontalPanel();
        JLabel argsLabel = new JLabel("    Args:", SwingConstants.RIGHT);
        argsLabel.setLabelFor(argsBodyContent);
        argsPanel.add(argsLabel);
        argsPanel.add(JTextScrollPane.getInstance(argsBodyContent));
        interfaceSettings.add(argsPanel);

        settingPanel.add(registrySettings);
        settingPanel.add(protocolSettings);
        settingPanel.add(consumerSettings);
        settingPanel.add(interfaceSettings);

        //全局布局设置
        setLayout(new BorderLayout(0, 5));
        setBorder(makeBorder());
        add(settingPanel,BorderLayout.CENTER);
    }

    @Override
    public void configure(TestElement element){
        super.configure(element);
        log.debug("赋值给gui");
        DubboSampleNew sample = (DubboSampleNew) element;
        registryProtocolText.setSelectedItem(sample.getRegistryProtocol());
        rpcProtocolText.setSelectedItem(sample.getRpcProtocol());
        addressText.setText(sample.getAddress());
        versionText.setText(sample.getVersion());
        timeoutText.setText(sample.getTimeout());
        retriesText.setText(sample.getRetries());
        groupText.setText(sample.getGroup());
        connectionsText.setText(sample.getConnections());
        loadbalanceText.setSelectedItem(sample.getLoadbalance());
        asyncText.setSelectedItem(sample.getAsync());
        clusterText.setText(sample.getCluster());
        interfaceText.setText(sample.getInterface());
        methodText.setText(sample.getMethod());
        argsBodyContent.setInitialText(sample.getArgs());
    }

    @Override
    public TestElement createTestElement() {
        log.debug("创建sample对象");
        DubboSampleNew sampleNew = new DubboSampleNew();
        modifyTestElement(sampleNew);
        return sampleNew;
    }

    @Override
    public String getLabelResource() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void modifyTestElement(TestElement testElement) {
        log.debug("gui数据赋值给sample");
        super.configureTestElement(testElement);
        DubboSampleNew sampleNew = (DubboSampleNew) testElement;
        sampleNew.setRegistryProtocol(registryProtocolText.getSelectedItem().toString());
        sampleNew.setRpcProtocol(rpcProtocolText.getSelectedItem().toString());
        sampleNew.setAddress(addressText.getText());
        sampleNew.setTimeout(timeoutText.getText());
        sampleNew.setVersion(versionText.getText());
        sampleNew.setRetries(retriesText.getText());
        sampleNew.setGroup(groupText.getText());
        sampleNew.setConnections(connectionsText.getText());
        sampleNew.setLoadbalance(loadbalanceText.getSelectedItem().toString());
        sampleNew.setAsync(asyncText.getSelectedItem().toString());
        sampleNew.setCluster(clusterText.getText());
        sampleNew.setInterfaceName(interfaceText.getText());
        sampleNew.setMethod(methodText.getText());
        sampleNew.setArgs(argsBodyContent.getText());
    }

    @Override
    public void clearGui() {
        log.debug("清空gui数据");
        super.clearGui();
        registryProtocolText.setSelectedIndex(1);
        rpcProtocolText.setSelectedIndex(0);
        addressText.setText("");
        timeoutText.setText(DubboSample.DEFAULT_TIMEOUT);
        versionText.setText(DubboSample.DEFAULT_VERSION);
        retriesText.setText(DubboSample.DEFAULT_RETRIES);
        clusterText.setText(DubboSample.DEFAULT_CLUSTER);
        groupText.setText(DubboSample.DEFAULT_GROUP);
        connectionsText.setText(DubboSample.DEFAULT_CONNECTIONS);
        loadbalanceText.setSelectedIndex(0);
        asyncText.setSelectedIndex(0);
        interfaceText.setText("");
        methodText.setText("");
        argsBodyContent.setInitialText("");
    }

    @Override
    public String getStaticLabel() {
        return "Dubbo Sample JSON";
    }
}
