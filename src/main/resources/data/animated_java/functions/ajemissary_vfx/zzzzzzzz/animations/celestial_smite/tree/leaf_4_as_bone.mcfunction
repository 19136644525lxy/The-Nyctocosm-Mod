execute if entity @s[tag=aj.ajemissary_vfx.bone.outercircle] run data modify entity @s {} merge value {transformation:[-0.00030539629874309384f,0f,-0.00008982879173811773f,0f,0f,0.0003183333333333334f,0f,0.3674605559729527f,0.00008982879173811773f,0f,-0.00030539629874309384f,0f,0f,0f,0f,1f],start_interpolation:0}
execute if entity @s[tag=aj.ajemissary_vfx.bone.innercircle] run data modify entity @s {} merge value {transformation:[-12.126141786967965f,0f,3.5667644619516388f,0f,0f,12.639822916666663f,0f,-0.30794844102178814f,-3.5667644619516388f,0f,-12.126141786967965f,0f,0f,0f,0f,1f],start_interpolation:0}
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint] at @s on origin run tp @s ^0 ^0 ^-0.00014 ~0 ~0
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint2] at @s on origin run tp @s ^0 ^0 ^0.00014 ~0 ~0
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint3] at @s on origin run tp @s ^0.00014 ^0 ^0 ~0 ~0
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint4] at @s on origin run tp @s ^-0.00014 ^0 ^0 ~0 ~0
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint5] at @s on origin run tp @s ^0 ^0.00014 ^0 ~0 ~0
execute if entity @s[tag=aj.ajemissary_vfx.locator_origin.explosionpoint6] at @s on origin run tp @s ^0 ^-0.00014 ^0 ~0 ~0