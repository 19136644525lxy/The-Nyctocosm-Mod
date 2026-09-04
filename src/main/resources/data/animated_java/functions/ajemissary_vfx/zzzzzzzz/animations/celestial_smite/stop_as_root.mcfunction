scoreboard players set @s aj.ajemissary_vfx.animation.celestial_smite.local_anim_time 0
tag @s remove aj.ajemissary_vfx.animation.celestial_smite
execute on passengers run data modify entity @s interpolation_duration set value 0
tag @s add aj.ajemissary_vfx.disable_command_keyframes
function animated_java:ajemissary_vfx/zzzzzzzz/animations/celestial_smite/tree/leaf_0
tag @s remove aj.ajemissary_vfx.disable_command_keyframes