execute if entity @s[tag=aj.ajemissary_vfx.bone.outercircle] run data modify entity @s {} merge value {transformation:[25.16536032261417f,0f,-10.395462749367589f,0f,0f,27.22794531250003f,0f,2.845224609375003f,10.395462749367589f,0f,25.16536032261417f,0f,0f,0f,0f,1f],start_interpolation:0}
execute if entity @s[tag=aj.ajemissary_vfx.bone.innercircle] run data modify entity @s {} merge value {transformation:[25.16536032261417f,0f,10.395462749367582f,0f,0f,27.22794531250003f,0f,-2.384497070312502f,-10.395462749367582f,0f,25.16536032261417f,0f,0f,0f,0f,1f],start_interpolation:0}
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint] at @s on origin run tp @s ^0 ^0 ^-0.00014 ~0 ~0
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint2] at @s on origin run tp @s ^0 ^0 ^0.00014 ~0 ~0
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint3] at @s on origin run tp @s ^0.00014 ^0 ^0 ~0 ~0
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint4] at @s on origin run tp @s ^-0.00014 ^0 ^0 ~0 ~0
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint5] at @s on origin run tp @s ^0 ^0.00014 ^0 ~0 ~0
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint6] at @s on origin run tp @s ^0 ^-0.00014 ^0 ~0 ~0