/*          Copyright © 2014 Stanislav Petriakov
// Distributed under the Boost Software License, Version 1.0.
//    (See accompanying file LICENSE_1_0.txt or copy at
//          http://www.boost.org/LICENSE_1_0.txt)
*/
package kg.fucking.shit.kgl2;

/**
 * Created by 4eRT on 29.12.2014.
 */
public class Roof extends Mesh {
    public Roof(float width, float height, float depth) {
        width /= 2;
        depth /= 2;

        // +0.1f - fix for overlap
        float vertices[] = {
                -width - width / 4, height - height / 8 + 0.1f, depth + depth / 4,
                -width - width / 4, height + height / 2 + 0.1f, 0,
                -width - width / 4, height - height / 8 + 0.1f, -depth - depth / 4,
                width + width / 4, height - height / 8 + 0.1f, -depth - depth / 4,
                width + width / 4, height + height / 2 + 0.1f, 0,
                width + width / 4, height - height / 8 + 0.1f, depth + depth / 4,
        };

        short indices[] = {
                0, 1, 4,
                0, 4, 5,
                1, 2, 3,
                1, 4, 3,
        };

        float textureCoordinates[] = {
                0.0f, 2.0f,
                0.0f, 0.0f,
                0.0f, 2.0f,
                2.0f, 2.0f,
                2.0f, 0.0f,
                2.0f, 2.0f,
        };

        setIndices(indices);
        setVertices(vertices);
        setTextureCoordinates(textureCoordinates);
    }

}
