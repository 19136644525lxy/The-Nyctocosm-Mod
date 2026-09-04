scoreboard players add @s aj.ajemissary_vfx.animation.celestial_smite.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajemissary_vfx.animation.celestial_smite.local_anim_time
function animated_java:ajemissary_vfx/zzzzzzzz/animations/celestial_smite/apply_frame_as_root
execute if score @s aj.ajemissary_vfx.animation.celestial_smite.local_anim_time matches 50.. run function animated_java:ajemissary_vfx/zzzzzzzz/animations/celestial_smite/end