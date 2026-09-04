scoreboard players add @s aj.ajspellplane.animation.idle.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajspellplane.animation.idle.local_anim_time
function zzz_ajspellplane_internal:animations/idle/apply_frame_as_root
execute if score @s aj.ajspellplane.animation.idle.local_anim_time matches 400.. run function zzz_ajspellplane_internal:animations/idle/end