OSGI bootstrap projects
==========

To run :

```
pax-runner/setenv.bat
gradlew build
```

```
pax-run --log=debug --args=file:org.lenition.osgi.connector/felix.run
```
or
```
pax-run --log=debug --args=file:org.lenition.osgi.whiteboard/felix.run
pax-run --log=debug --args=file:org.lenition.osgi.simple-service/felix.run
```



Service Frameworks, Tools and Component Models
==========
|                      | ...connector  | ...whiteboard   | ...simple  |  ...wab.bootstrap | ...scala (standalone) |
| -------------------- |-------------------|-------------|-------------|-----------------|------------------------
| osgi-jax-rs-connector| yes               |             |             |                |    yes                    |
| whiteboard pattern   |                   |  yes        |             |                |                          |
| SCR/DS               | yes               |  yes        |     yes     |                |                          |


Monolithic Code -> many dependencies + tight coupling -> risk averse development -> slow development

Make modularity a high priority design criteria -> team autonomy
 - hide implementation details (Export-Package)

Best Practices:
 - Avoid start order dependencies
 - Use Import-Package rather than Require-Bundle (http://www.ibm.com/developerworks/websphere/techjournal/1007_charters/1007_charters.html#sec8)
 - Use Semantic Versioning (i.e, [1.1.2,2.0) )
 - Version all the bundles
 - Always export packages with a version
 - gradle version == bundle version == export packages versions
 - Export all packages except impl.* and internal.*
 - Separate API and Implementation Bundles
 - Fail quickly
 - Also use Executor service for long/slow operations performed as a callback
 - pass a ClassLoader that the framework should use when it performs its lookup by name
 - optional imports (resolution:="optional") can affect start order and cause side-effects


Challenges:
 - bundlize transitive dependencies
 - avoid split packages and duplicate classes (build tool to detect)


TODO:
http://www.jayway.com/2010/02/09/building-osgi-bundles-with-scala-and-gradle/
https://github.com/seijoed/osgi-starter/blob/master/itest/src/test/java/com/packt/osgi/starter/tests/ProducerAndConsumerTest.java
http://aries.apache.org/
http://my.safaribooksonline.com/book/programming/java/9781617290138/chapter-4dot-packaging-your-enterprise-osgi-applications/ch04lev1sec3_html


http://techbus.safaribooksonline.com/book/programming/java/9781617290138/part-2dot-building-better-enterprise-osgi-applications/118?percentage=&reader=#X2ludGVybmFsX0J2ZGVwRmxhc2hSZWFkZXI/eG1saWQ9OTc4MTYxNzI5MDEzOC83OA==
http://techbus.safaribooksonline.com/book/operating-systems-and-server-administration/virtualization/9781449345143/idot-introducing-modularity-in-java/ch05_html

BndTools:
https://bitbucket.org/amdatu/showcase/src/1e078853256ef1088295f9d3df246ce8b113d8aa/build.gradle?at=master
https://github.com/tux2323/bndtools-gradle-demo


WAB:
http://blog.knowhowlab.org/2010/10/osgi-tutorial-4-ways-to-activate-code.html

BluePrint :
http://aries.apache.org/modules/blueprintannotation.html

dosgi:
http://wiki.osgi.org/wiki/Remote_Service_Admin
http://wiki.eclipse.org/EIG:Getting_Started_with_OSGi_Remote_Services
http://bryanhunt.wordpress.com/2009/06/20/remote-declarative-osgi-services/
http://www.noway.es/remote-OSGI-example
http://www.liquid-reality.de/display/liquid/2013/02/13/Apache+Karaf+Tutorial+Part+8+-+Distributed+OSGi
http://eclipsesource.com/blogs/2008/12/19/rfc-119-and-ecf-part-1/

Apache CXF:
http://cxf.apache.org/distributed-osgi-greeter-demo-walkthrough.html
http://svn.apache.org/repos/asf/cxf/dosgi/trunk/samples/greeter/
http://camel.apache.org/cxf-example-osgi.html
http://cxf.apache.org/distributed-osgi-reference.html#DistributedOSGiReference-DistributedOSGiReferenceGuide

Karaf shell:
http://icodebythesea.blogspot.com/2011/11/creating-your-own-apache-karaf-console.html
http://karaf.apache.org/manual/latest-2.2.x/developers-guide/extending-console.html

Aspect Service:
http://felix.apache.org/site/apache-felix-dependency-manager-using-annotations-quick-tour.html

"""" from http://www.slideshare.net/tcng3716/enterprise-osgi-at-ebay-12821567"
Dynamic Classloading:
- Problematic usage
   - Class.forName()
   - ClassLoader loadClass or getResource
   - Thread context class loader
- Short-term fix
   - DynamicImport-Package: *
- Long-term fix
   - Client passes class or classloader to framework
""""""




#### Karaf Shell ####
# add options: -Dkaraf.startLocalConsole=true -Dkaraf.startRemoteShell=true
#scan-bundle:mvn:org.apache.felix.karaf.shell/org.apache.felix.karaf.shell.console/1.6.0
#scan-bundle:mvn:org.fusesource.jansi/jansi/1.9
#scan-bundle:mvn:jline/jline/2.9
#scan-bundle:mvn:org.apache.karaf.jaas/org.apache.karaf.jaas.boot/3.0.0.RC1
#scan-bundle:mvn:org.apache.felix.gogo/org.apache.felix.gogo.runtime/0.4.0
#scan-bundle:mvn:org.apache.felix.gogo/org.apache.felix.gogo.commands/0.4.0

#### Blueprint ####
#scan-bundle:mvn:org.apache.aries.blueprint/org.apache.aries.blueprint/0.4
#scan-bundle:mvn:org.apache.aries.proxy/org.apache.aries.proxy/0.4
#scan-bundle:mvn:org.apache.aries/org.apache.aries.util/0.4


#scan-bundle:mvn:org.osgi/org.osgi.compendium/4.3.1
#scan-bundle:mvn:org.apache.felix/org.apache.felix.webconsole.plugins.event/1.0.2@5
#scan-bundle:mvn:org.apache.felix/org.apache.felix.webconsole.plugins.memoryusage/1.0.0@5

#### Pax Web (HTTP Service, WAB support, JSP support) ####
#scan-bundle:mvn:org.ops4j.pax.web/pax-web-api/2.1.2
scan-bundle:mvn:org.ops4j.pax.web/pax-web-jetty-bundle/2.1.2
#scan-bundle:mvn:org.ops4j.pax.web/pax-web-extender-war/2.1.2
#scan-bundle:mvn:org.ops4j.pax.web/pax-web-jsp/2.1.2
