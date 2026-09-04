execute if entity @s[tag=aj.ajemissary_vfx.bone.outercircle] run data modify entity @s {} merge value {transformation:[-2.5321775338145955f,0f,-0.711464972032111f,0f,0f,2.6302291666666653f,0f,0.28007812499999996f,0.711464972032111f,0f,-2.5321775338145955f,0f,0f,0f,0f,1f],start_interpolation:0}
execute if entity @s[tag=aj.ajemissary_vfx.bone.innercircle] run data modify entity @s {} merge value {transformation:[-2.5321775338145955f,0f,0.7114649720321117f,0f,0f,2.6302291666666653f,0f,-0.23453125000000008f,-0.7114649720321117f,0f,-2.5321775338145955f,0f,0f,0f,0f,1f],start_interpolation:0}
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint] at @s on origin run tp @s ^0 ^0 ^-0.00014 ~0 ~0
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint2] at @s on origin run tp @s ^0 ^0 ^0.00014 ~0 ~0
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint3] at @s on origin run tp @s ^0.00014 ^0 ^0 ~0 ~0
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint4] at @s on origin run tp @s ^-0.00014 ^0 ^0 ~0 ~0
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint5] at @s on origin run tp @s ^0 ^0.00014 ^0 ~0 ~0
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint6] at @s on origin run tp @s ^0 ^-0.00014 ^0 ~0 ~0