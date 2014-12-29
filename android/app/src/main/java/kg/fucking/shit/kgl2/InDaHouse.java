/*          Copyright © 2014 Stanislav Petiakov
// Distributed under the Boost Software License, Version 1.0.
//    (See accompanying file LICENSE_1_0.txt or copy at
//          http://www.boost.org/LICENSE_1_0.txt)
*/
package kg.fucking.shit.kgl2;

/**
 * Created by 4eRT on 29.12.2014.
 */
public class InDaHouse extends Mesh {
    public InDaHouse(float width, float height, float depth) {
        width /= 2;
        depth /= 2;

        float vertices[] = {
                -width, 0, -depth,
                -width, 0, depth,
                -width, height, depth,
                -width, height, -depth,
                width, height, -depth,
                width, height, depth,
                width, 0, depth,
                width, 0, -depth,
        };

        short indices[] = {
                0, 1, 2,
                0, 2, 3,
                0, 3, 4,
                0, 4, 7,
                4, 5, 7,
                5, 6, 7,
                5, 6, 1,
                5, 1, 2,
        };

        float textureCoordinates[] = {
                1.0f, 1.0f,
                0.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f,
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 1.0f,
        };

        setIndices(indices);
        setVertices(vertices);
        setTextureCoordinates(textureCoordinates);
    }
}
