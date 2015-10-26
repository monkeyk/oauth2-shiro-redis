#oauth2-shiro-redis

<p>
  Integrate <a href="http://git.oschina.net/mkk/oauth2-shiro">oauth2-shiro</a> with <strong>Redis</strong>
</p>


<div>
    <h3>说明</h3>
    <p>该项目具有 oauth2-shiro 的所有功能, 并添加了对 Redis 的支持.  是商业项目(非开源)</p>
    <p>从 oauth2-shiro fork 的版本: 0.1-rc</p>
    <p>项目使用的 Redis 版本信息
        <br/>
        spring-data-redis   -> 1.5.2.RELEASE
        <br/>
        jedis  ->  2.7.3
    </p>

</div>


<div>
    <h3>使用注意</h3>
    <p>authz 与 resources 模块中配置的 Redis 必须是同一个Redis的连接信息, 方可正常工作</p>
    <p>在项目中,使用Redis做缓存, 提高性能,同时也将数据存入MYSQL数据库; 也支持去掉MYSQL,只使用Redis(需要修改配置实现)</p>

</div>


<div>
    <h3>Project Logs</h3>
    <p>记录项目的变化与发展历程</p>

    <ol>
        <li><p>2015-10-21     从oauth2-shiro fork源代码到本项目中</p></li>
    </ol>
</div>


<div>
    <h3>技术支持联系方式</h3>
    <p>Telephone:   13308231107</p>
    <p>Email:   sz@monkeyk.com</p>
</div>

