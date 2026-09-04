execute if entity @s[tag=aj.ajemissary_vfx.bone.outercircle] run data modify entity @s {} merge value {transformation:[-2.548715718120102f,0f,-13.347190263110084f,0f,0f,13.588356770833371f,0f,1.426142578125004f,13.347190263110084f,0f,-2.548715718120102f,0f,0f,0f,0f,1f],start_interpolation:0}
execute if entity @s[tag=aj.ajemissary_vfx.bone.innercircle] run data modify entity @s {} merge value {transformation:[-2.5487157181200986f,0f,13.347190263110084f,0f,0f,13.588356770833371f,0f,-1.1949365234375033f,-13.347190263110084f,0f,-2.5487157181200986f,0f,0f,0f,0f,1f],start_interpolation:0}
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint] at @s on origin run tp @s ^0 ^0 ^-0.00014 ~0 ~0
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint2] at @s on origin run tp @s ^0 ^0 ^0.00014 ~0 ~0
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint3] at @s on origin run tp @s ^0.00014 ^0 ^0 ~0 ~0
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint4] at @s on origin run tp @s ^-0.00014 ^0 ^0 ~0 ~0
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint5] at @s on origin run tp @s ^0 ^0.00014 ^0 ~0 ~0
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint6] at @s on origin run tp @s ^0 ^-0.00014 ^0 ~0 ~0