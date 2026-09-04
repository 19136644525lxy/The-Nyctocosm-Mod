scoreboard players operation @s aj.id = .aj.last_id aj.id
tag @s remove aj.new
function #ajmobilespellplane:on_summon_as_rig_entities
execute if entity @s[tag=aj.ajmobilespellplane.bone] run function zzz_ajmobilespellplane_internal:summon/as_bones
execute if entity @s[tag=aj.ajmobilespellplane.locator] run function zzz_ajmobilespellplane_internal:summon/as_locators
execute if entity @s[tag=aj.ajmobilespellplane.camera] run function zzz_ajmobilespellplane_internal:summon/as_cameras