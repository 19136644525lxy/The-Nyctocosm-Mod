execute if entity @s[tag=aj.ajemissary_vfx.bone.outercircle] run data modify entity @s {} merge value {transformation:[-0.00031782786981410086f,0f,-0.00001793199042332163f,0f,0f,0.0003183333333333334f,0f,0.0711089523854245f,0.00001793199042332163f,0f,-0.00031782786981410086f,0f,0f,0f,0f,1f],start_interpolation:0}
execute if entity @s[tag=aj.ajemissary_vfx.bone.innercircle] run data modify entity @s {} merge value {transformation:[-2.1410238562154773f,0f,0.12079752259679245f,0f,0f,2.1444288736979167f,0f,-0.05959273455108941f,-0.12079752259679245f,0f,-2.1410238562154773f,0f,0f,0f,0f,1f],start_interpolation:0}
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint] at @s on origin run tp @s ^0 ^0 ^-0.00014 ~0 ~0
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint2] at @s on origin run tp @s ^0 ^0 ^0.00014 ~0 ~0
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint3] at @s on origin run tp @s ^0.00014 ^0 ^0 ~0 ~0
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint4] at @s on origin run tp @s ^-0.00014 ^0 ^0 ~0 ~0
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint5] at @s on origin run tp @s ^0 ^0.00014 ^0 ~0 ~0
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint6] at @s on origin run tp @s ^0 ^-0.00014 ^0 ~0 ~0