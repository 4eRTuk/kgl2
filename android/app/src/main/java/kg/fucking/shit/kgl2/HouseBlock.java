/*          Copyright © 2014 Stanislav Petiakov
// Distributed under the Boost Software License, Version 1.0.
//    (See accompanying file LICENSE_1_0.txt or copy at
//          http://www.boost.org/LICENSE_1_0.txt)
*/
package kg.fucking.shit.kgl2;

/**
 * Created by 4eRT on 28.12.2014.
 */
public class HouseBlock extends Mesh {
    public HouseBlock(float width, float height, float depth) {
        width /= 2;
        depth /= 2;

        float vertices[] = {
                -width, 0, -depth,
                -width, 0, depth,
                -width, height, depth,
                -width, height + height / 2, 0,
                -width, height, -depth,
                width, height, -depth,
                width, height + height / 2, 0,
                width, height, depth,
                width, 0, depth,
                width, 0, -depth,
        };

        short indices[] = {
                0, 1, 2,
                0, 2, 4,
                2, 3, 4,
                0, 4, 5,
                0, 5, 9,
                0, 9, 8,
                0, 8, 1,
                1, 2, 7,
                1, 7, 8,
                2, 3, 6,
                2, 6, 7,
                4, 5, 6,
                4, 6, 3,
                5, 6, 7,
                5, 7, 8,
                5, 8, 9,};

        float textureCoordinates[] = {
                0.0f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.0f,
                0.5f, 0.5f,
                0.0f, 0.0f,
                1.0f, 0.0f,
                0.5f, 0.5f,
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,
        };

        setIndices(indices);
        setVertices(vertices);
        setTextureCoordinates(textureCoordinates);
    }
}
