/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kafka.javaapi

import kafka.common.{TopicAndPartition, OffsetMetadataAndError}
import collection.JavaConverters._
import java.nio.ByteBuffer

class OffsetCommitRequest(groupId: String,
                          requestInfo: java.util.Map[TopicAndPartition, OffsetMetadataAndError],
                          versionId: Short,
                          correlationId: Int,
                          clientId: String) {
  val underlying = {
    val scalaMap = requestInfo.asScala.toMap
    kafka.api.OffsetCommitRequest(
      groupId = groupId,
      requestInfo = scalaMap,
      versionId = versionId,
      correlationId = correlationId,
      clientId = clientId
    )
  }


  override def toString = underlying.toString


  override def equals(other: Any) = canEqual(other) && {
    val otherOffsetRequest = other.asInstanceOf[kafka.javaapi.OffsetCommitRequest]
    this.underlying.equals(otherOffsetRequest.underlying)
  }


  def canEqual(other: Any) = other.isInstanceOf[kafka.javaapi.OffsetCommitRequest]


  override def hashCode = underlying.hashCode

}
