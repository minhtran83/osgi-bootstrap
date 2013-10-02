OSGI bootstrap projects
==========

To run :

```
pax-runner/setenv.bat
pax-run --log=debug --args=file:jaxrs-connector/felix.run
```


Monolithic Code -> many dependencies + tight coupling -> risk averse development -> slow development

Make modularity a high priority design criteria -> team autonomy
 - hide implementation details (Export-Package)

Best Practices:
 - Avoid start order dependencies
 - Use Import-Package rather than Require-Bundle
 - Semantic Versioning (i.e, [1.1.2,2.0) )
 - gradle version == bundle version == export packages versions
 - generally, export all packages except impl.* and internal.*


Challenges:
 - bundlize transitive dependencies
 - avoid split packages and duplicate classes (build tool to detect)
 -


TODO:
http://aries.apache.org/
http://my.safaribooksonline.com/book/programming/java/9781617290138/chapter-4dot-packaging-your-enterprise-osgi-applications/ch04lev1sec3_html
http://pic.dhe.ibm.com/infocenter/wasinfo/v7r0/index.jsp?topic=%2Fcom.ibm.websphere.osgifep.multiplatform.doc%2Ftopics%2Fthread_ta_dev_createapp.html
http://stackoverflow.com/questions/9316578/creating-a-complete-osgi-application-with-felix-maven


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