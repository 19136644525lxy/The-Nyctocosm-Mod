scoreboard players operation @s aj.id = .aj.last_id aj.id
tag @s remove aj.new
function #ajsorcerer:on_summon_as_rig_entities
execute if entity @s[tag=aj.ajsorcerer.bone] run function zzz_ajsorcerer_internal:summon/as_bones
execute if entity @s[tag=aj.ajsorcerer.locator] run function zzz_ajsorcerer_internal:summon/as_locators
execute if entity @s[tag=aj.ajsorcerer.camera] run function zzz_ajsorcerer_internal:summon/as_cameras