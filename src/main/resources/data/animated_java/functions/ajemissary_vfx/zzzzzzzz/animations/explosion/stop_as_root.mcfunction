scoreboard players set @s aj.ajemissary_vfx.animation.explosion.local_anim_time 0
tag @s remove aj.ajemissary_vfx.animation.explosion
execute on passengers run data modify entity @s interpolation_duration set value 0
tag @s add aj.ajemissary_vfx.disable_command_keyframes
function animated_java:ajemissary_vfx/zzzzzzzz/animations/explosion/tree/leaf_0
tag @s remove aj.ajemissary_vfx.disable_command_keyframes