/*          Copyright © 2014 Stanislav Petriakov
// Distributed under the Boost Software License, Version 1.0.
//    (See accompanying file LICENSE_1_0.txt or copy at
//          http://www.boost.org/LICENSE_1_0.txt)
*/
package kg.fucking.shit.kgl2;

/**
 * Created by 4eRT on 28.12.2014.
 */
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

public class Group extends Mesh {
    private final Vector<Mesh> meshes = new Vector<Mesh>();

    // draw each mesh
    @Override
    public void draw(GL10 gl) {
        int size = meshes.size();

        for (int i = 0; i < size; i++)
            if (meshes.size() > 0)   // runtime fix > scenes' redraw on fast events (multiclick)
                meshes.get(i).draw(gl);
    }

    // add mesh to group
    public boolean add(Mesh object) {
        return meshes.add(object);
    }

    // delete all meshes
    public void clear() {
        meshes.clear();
    }
}