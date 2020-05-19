package oncecellboy.gis;

import org.geotools.metadata.iso.citation.Citations;
import org.geotools.referencing.ReferencingFactoryFinder;
import org.geotools.referencing.factory.PropertyAuthorityFactory;
import org.geotools.referencing.factory.ReferencingFactoryContainer;
import org.geotools.util.factory.Hints;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class GISInit {
    /**
     * CRS 등록 초기화
     *
     * @param path
     */
    public void CoordinateReferenceSystem(String path) throws IOException {
        URL epsg = new URL(path);
        Hints hints = new Hints(Hints.CRS_AUTHORITY_FACTORY, PropertyAuthorityFactory.class);
        ReferencingFactoryContainer referencingFactoryContainer =
                ReferencingFactoryContainer.instance(hints);

        PropertyAuthorityFactory factory = new PropertyAuthorityFactory(
                referencingFactoryContainer, Citations.fromName("EPSG"), epsg);

        ReferencingFactoryFinder.addAuthorityFactory(factory);
        ReferencingFactoryFinder.scanForPlugins(); // hook everything up
    }
}
