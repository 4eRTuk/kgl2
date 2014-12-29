/*          Copyright © 2014 Stanislav Petiakov
// Distributed under the Boost Software License, Version 1.0.
//    (See accompanying file LICENSE_1_0.txt or copy at
//          http://www.boost.org/LICENSE_1_0.txt)
*/
package kg.fucking.shit.kgl2;

/**
 * Created by 4eRT on 28.12.2014.
 */
public class Plane extends Mesh {   // simple plane, draws at center by x-z axes
    public Plane(float width, float depth) {
        width /= 2;
        depth /= 2;

        float vertices[] = { -width,  0, -depth,
                -width,  0, depth,
                width, 0, depth,
                width, 0,  -depth,
        };

        short indices[] = { 0, 1, 2,
                0, 2, 3,
        };

        float textureCoordinates[] = { 0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.0f,
        };

        setIndices(indices);
        setVertices(vertices);
        setTextureCoordinates(textureCoordinates);
    }

    // for seamless textures
    // or you can divide vertices in more segments and use repeat mode by _S, _T
    public void setTextureZoom(float zoom) {
        float textureCoordinates[] = { 0.0f, 0.0f,
                0.0f, zoom,
                zoom, zoom,
                zoom, 0.0f,
        };

        setTextureCoordinates(textureCoordinates);
    }
}
