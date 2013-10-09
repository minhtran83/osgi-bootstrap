OSGI bootstrap projects
==========

To run :

```
pax-runner/setenv.bat
gradlew build
```

```
pax-run --log=debug --args=file:org.lenition.osgi.jaxrs-connector/felix.run
```
or
```
pax-run --log=debug --args=file:whiteboard/felix.run
pax-run --log=debug --args=file:simple-service/felix.run
```



Service Frameworks, Tools and Component Models
==========
|                      | /org.lenition.osgi.jaxrs-connector  | /whiteboard | /simple-service  |
| -------------------- |-------------------|-------------|-------------|
| osgi-jax-rs-connector| yes               |             |
| whiteboard pattern   |                   |  yes        |
| SCR/DS               | yes               |  yes         |     yes       |


Monolithic Code -> many dependencies + tight coupling -> risk averse development -> slow development

Make modularity a high priority design criteria -> team autonomy
 - hide implementation details (Export-Package)

Best Practices:
 - Avoid start order dependencies
 - Use Import-Package rather than Require-Bundle (http://www.ibm.com/developerworks/websphere/techjournal/1007_charters/1007_charters.html#sec8)
 - Semantic Versioning (i.e, [1.1.2,2.0) )
 - Version all the bundles
 - gradle version == bundle version == export packages versions
 - generally, export all packages except impl.* and internal.*
 - Separate API from Implementation
 - Fail quickly
 - Also use Executor service for long/slow operations performed as a callback
 - pass a ClassLoader that the framework should use when it performs its lookup by name


Challenges:
 - bundlize transitive dependencies
 - avoid split packages and duplicate classes (build tool to detect)
 -


TODO:
http://aries.apache.org/
http://my.safaribooksonline.com/book/programming/java/9781617290138/chapter-4dot-packaging-your-enterprise-osgi-applications/ch04lev1sec3_html
http://pic.dhe.ibm.com/infocenter/wasinfo/v7r0/index.jsp?topic=%2Fcom.ibm.websphere.osgifep.multiplatform.doc%2Ftopics%2Fthread_ta_dev_createapp.html
http://stackoverflow.com/questions/9316578/creating-a-complete-osgi-application-with-felix-maven


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

IntelliJ plugins:
http://plugins.jetbrains.com/plugin/?webide&id=7009

BndTools:
https://github.com/tux2323/bndtools-gradle-demo

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
