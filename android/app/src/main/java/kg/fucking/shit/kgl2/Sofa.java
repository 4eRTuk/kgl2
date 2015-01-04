/*          Copyright © 2014 Stanislav Petriakov
// Distributed under the Boost Software License, Version 1.0.
//    (See accompanying file LICENSE_1_0.txt or copy at
//          http://www.boost.org/LICENSE_1_0.txt)
*/
package kg.fucking.shit.kgl2;

/**
 * Created by 4eRT on 29.12.2014.
 */
public class Sofa extends Mesh {
    public Sofa(float w, float h, float d) {
        w /= 5;
        d -= 2;
        h /= 4;

        // oh gosh it's fucking creepy :P
        // don't use it in real life!!!
        // use .obj, .msh or smthg else instead
        // ONLY FOR LABS OR TUTORIALS!!!
        float vertices[] = {
                0, 0, 0,
                0, 0, d,
                0, h, d,
                0, h, 0,

                w / 4, h, 0,
                w / 4, h / 4, 0,
                w / 4, h, d,
                w / 4, h / 4, d,

                w, h / 4, d,
                w, 0, d,
                w, h / 4, 0,
                w, 0, 0,
        };

        short indices[] = {
                0, 1, 2,
                0, 2, 3,
                0, 11, 9,
                0, 1, 9,
                2, 6, 7,
                1, 2, 7,
                1, 7, 8,
                1, 8, 9,
                2, 3, 6,
                3, 6, 4,
                4, 6, 7,
                4, 7, 5,
                8, 7, 5,
                8, 10, 5,
                8, 10, 9,
                9, 10, 11,
                0, 10, 11,
                0, 10, 5,
                0, 5, 4,
                0, 4, 3,
        };

        float textureCoordinates[] = {
                1.0f, 1.0f,
                0.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f,

                0.66f, 0.0f,
                0.66f, 0.66f,
                0.33f, 0.0f,
                0.33f, 0.66f,

                1.0f, 0.66f,
                1.0f, 1.0f,
                0.0f, 0.66f,
                0.0f, 1.0f,
        };

        setIndices(indices);
        setVertices(vertices);
        setTextureCoordinates(textureCoordinates);
    }
}
