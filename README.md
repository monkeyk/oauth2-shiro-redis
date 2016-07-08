#oauth2-shiro-redis

<p>
  Integrate <a href="http://git.oschina.net/mkk/oauth2-shiro">oauth2-shiro</a> with <strong>Redis</strong>
</p>


<hr/>
<h3>注意: 该branch不再支持MYSQL数据库操作, 只支持Redis</h3>
<hr/>

<div>
    <h3>说明</h3>
    <p>该项目具有 oauth2-shiro 的所有功能, 并添加了对 Redis 的支持</p>
    <p>从 oauth2-shiro fork 的版本: 0.1-rc</p>
    <p>项目使用的 Redis 版本信息
        <br/>
        spring-data-redis   -> 1.5.2.RELEASE
        <br/>
        jedis  ->  2.7.3
    </p>

</div>


<div>
    <h3>功能变化</h3>
    <p>相比 oauth2-shiro 项目, 添加并支持更多的功能与配置</p>
    <ol>
        <li><p>支持Redis连接属性更多的设置, 详见配置文件 <em>resources.properties</em>, <em>authz.properties</em></p></li>
        <li><p>提供对 ClientDetails 的操作支持, 详见 <code>ClientDetailsService.java</code></p></li>
        <li><p>重构 ClientDetails, 使其支持 序列化(Serializable)</p></li>
        <li><p>添加配置属性 <em>remove.token.expired</em>, 支持当检测到 access_token 过期时删除对应的 AccessToken 数据</p></li>
        <li><p>根据需要可去掉MYSQL数据库支持, 只使用Redis, 详见 branch: <code>oauth2-shiro-redis(single)</code></p></li>
        <li><p>重构 OAUTH2 业务实现的代码, 使结构,代码更清晰, 可读更强</p></li>
    </ol>
</div>


<div>
    <h3>使用注意</h3>
    <p>authz 与 resources 模块中配置的 Redis 必须是同一个Redis的连接信息, 方可正常工作</p>
    <p>在项目中,使用Redis做缓存, 提高性能, 不再支持数据库操作</p>

</div>


<div>
    <h3>Project Logs</h3>
    <p>记录项目的变化与发展历程</p>

    <ol>
        <li><p>2015-10-21     从oauth2-shiro fork源代码到本项目中</p></li>
        <li><p>2015-10-27     创建branch: <a href="http://git.oschina.net/mkk/oauth2-shiro-redis/tree/redis/">redis</a>, 只支持Redis操作</p></li>
    </ol>
</div>


<div>
    <h3>技术支持联系方式</h3>
    <p>Email:   sz@monkeyk.com</p>
</div>
<p>
    <img src="http://77g1is.com1.z0.glb.clouddn.com/wechat_qrcode.jpg"/>
</p>
