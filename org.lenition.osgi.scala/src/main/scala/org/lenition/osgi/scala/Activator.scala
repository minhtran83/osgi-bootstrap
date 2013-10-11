package org.lenition.osgi.scala;

import org.osgi.framework._

class Activator extends BundleActivator {

    def start( context: BundleContext ) {
        var bundleNames = 
            context.getBundles()
                .map(b => b.getSymbolicName())
                .filter(b => b != context.getBundle())
                
        println("installed bundles:\n" + bundleNames.mkString("\n"))
        
    }

    def stop( context: BundleContext ) {
    
    }
}