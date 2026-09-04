scoreboard players add @s aj.ajsorcererm1.animation.shardhail.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajsorcererm1.animation.shardhail.local_anim_time
function zzz_ajsorcererm1_internal:animations/shardhail/apply_frame_as_root
execute if score @s aj.ajsorcererm1.animation.shardhail.local_anim_time matches 63.. run function zzz_ajsorcererm1_internal:animations/shardhail/end